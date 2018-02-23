package com.yossisegev.domain

/**
 * Created by Yossi Segev on 06/01/2018.
 */
import com.yossisegev.domain.common.DomainTestUtils
import com.yossisegev.domain.common.DomainTestUtils.Companion.generateMovieEntityList
import com.yossisegev.domain.common.TestMoviesCache
import com.yossisegev.domain.common.TestTransformer
import com.yossisegev.domain.entities.MovieEntity
import com.yossisegev.domain.entities.Optional
import com.yossisegev.domain.usecases.*
import io.reactivex.Observable
import junit.framework.Assert.assertNotNull
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class UseCasesTests {

    @Test
    fun getMovieDetailsById() {
        val movieEntity = DomainTestUtils.getTestMovieEntity(100)
        val movieRepository = Mockito.mock(MoviesRepository::class.java)
        val getMovieDetails = GetMovieDetails(TestTransformer(), movieRepository)

        Mockito.`when`(movieRepository.getMovie(100)).thenReturn(Observable.just(Optional.of(movieEntity)))

        getMovieDetails.getById(100).test()
                .assertValue { returnedMovieEntity ->
                    returnedMovieEntity.hasValue() &&
                            returnedMovieEntity.value?.id == 100
                }
                .assertComplete()
    }

    @Test
    fun getPopularMovies() {
        val movieRepository = Mockito.mock(MoviesRepository::class.java)
        Mockito.`when`(movieRepository.getMovies()).thenReturn(Observable.just(generateMovieEntityList()))
        val getPopularMovies = GetPopularMovies(TestTransformer(), movieRepository)
        getPopularMovies.observable().test()
                .assertValue { results -> results.size == 5 }
                .assertComplete()
    }

    @Test
    fun getPopularMoviesNoResultsReturnsEmpty() {
        val movieRepository = Mockito.mock(MoviesRepository::class.java)
        Mockito.`when`(movieRepository.getMovies()).thenReturn(Observable.just(emptyList()))
        val getPopularMovies = GetPopularMovies(TestTransformer(), movieRepository)
        getPopularMovies.observable().test()
                .assertValue { results -> results.isEmpty() }
                .assertComplete()
    }

    @Test
    fun saveMovieToFavorites() {
        val moviesCache = TestMoviesCache()
        val saveFavoriteMovie = SaveFavoriteMovie(TestTransformer(), moviesCache)
        val movieEntity = DomainTestUtils.getTestMovieEntity(1)
        saveFavoriteMovie.save(movieEntity).test()
                .assertValue { result -> result }
                .assertComplete()
        moviesCache.get(movieEntity.id).test()
                .assertValue { optionalMovieEntity ->
                    optionalMovieEntity.hasValue()
                            && optionalMovieEntity.value?.id == movieEntity.id
                }
    }

    @Test
    fun getFavoriteMovies() {
        val moviesCache = Mockito.mock(MoviesCache::class.java)
        Mockito.`when`(moviesCache.getAll()).thenReturn(Observable.just(generateMovieEntityList()))
        val getFavoriteMovies = GetFavoriteMovies(TestTransformer(), moviesCache)
        getFavoriteMovies.observable().test()
                .assertValue { results -> results.size == 5 }
                .assertComplete()
    }

    @Test
    fun removeFavoriteMovie() {
        val moviesCache = TestMoviesCache()
        val saveFavoriteMovie = SaveFavoriteMovie(TestTransformer(), moviesCache)
        val removeFavoriteMovies = RemoveFavoriteMovie(TestTransformer(), moviesCache)
        val movieEntity = DomainTestUtils.getTestMovieEntity(1)
        saveFavoriteMovie.save(movieEntity)
        assertNotNull(moviesCache.get(movieEntity.id))
        removeFavoriteMovies.remove(movieEntity).test()
                .assertValue { returnedValue -> !returnedValue }
                .assertComplete()

        moviesCache.get(movieEntity.id).test()
                .assertValue { optionalEntity -> !optionalEntity.hasValue() }
    }

    @Test
    fun searchMovies() {
        val movieRepository = Mockito.mock(MoviesRepository::class.java)
        val searchMovie = SearchMovie(TestTransformer(), movieRepository)
        `when`(movieRepository.search("test query")).thenReturn(Observable.just(generateMovieEntityList()))
        searchMovie.search("test query").test()
                .assertComplete()
                .assertValue { results -> results.size == 5 }
    }

    @Test
    fun testCheckFavoriteStatus() {
        val movieCache = mock(MoviesCache::class.java)
        `when`(movieCache.get(99)).thenReturn(Observable.just(Optional.empty()))
        `when`(movieCache.get(100)).thenReturn(Observable.just(Optional.of(DomainTestUtils.getTestMovieEntity(100))))
        val checkFavoriteStatus = CheckFavoriteStatus(TestTransformer(), movieCache)
        checkFavoriteStatus.check(99).test()
                .assertValue { result -> !result }
                .assertComplete()
        checkFavoriteStatus.check(100).test()
                .assertValue { result -> result }
                .assertComplete()


    }

}