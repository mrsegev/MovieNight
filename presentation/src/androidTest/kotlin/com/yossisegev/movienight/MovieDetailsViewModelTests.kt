package com.yossisegev.movienight

import android.arch.lifecycle.Observer
import android.support.test.annotation.UiThreadTest
import android.support.test.runner.AndroidJUnit4
import com.yossisegev.domain.MoviesCache
import com.yossisegev.domain.MoviesRepository
import com.yossisegev.domain.common.DomainTestUtils
import com.yossisegev.domain.common.TestTransformer
import com.yossisegev.domain.entities.Optional
import com.yossisegev.domain.usecases.CheckFavoriteStatus
import com.yossisegev.domain.usecases.GetMovieDetails
import com.yossisegev.domain.usecases.RemoveFavoriteMovie
import com.yossisegev.domain.usecases.SaveFavoriteMovie
import com.yossisegev.movienight.details.MovieDetailsViewModel
import com.yossisegev.movienight.details.MovieDetailsViewState
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*

/**
 * Created by Yossi Segev on 19/02/2018.
 */

@Suppress("UNCHECKED_CAST")
@RunWith(AndroidJUnit4::class)
class MovieDetailsViewModelTests {

    private val testMovieId = 100
    private val movieEntityMovieMapper = MovieEntityMovieMapper()
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var moviesRepository: MoviesRepository
    private lateinit var moviesCache: MoviesCache
    private lateinit var viewObserver: Observer<MovieDetailsViewState>
    private lateinit var errorObserver: Observer<Throwable>
    private lateinit var favoriteStateObserver: Observer<Boolean>

    @Before
    @UiThreadTest
    fun before() {
        moviesRepository = mock(MoviesRepository::class.java)
        moviesCache = mock(MoviesCache::class.java)
        val getMovieDetails = GetMovieDetails(TestTransformer(), moviesRepository)
        val saveFavoriteMovie = SaveFavoriteMovie(TestTransformer(), moviesCache)
        val removeFavoriteMovie = RemoveFavoriteMovie(TestTransformer(), moviesCache)
        val checkFavoriteStatus = CheckFavoriteStatus(TestTransformer(), moviesCache)
        movieDetailsViewModel = MovieDetailsViewModel(
                getMovieDetails,
                saveFavoriteMovie,
                removeFavoriteMovie,
                checkFavoriteStatus,
                movieEntityMovieMapper,
                testMovieId)

        viewObserver = mock(Observer::class.java) as Observer<MovieDetailsViewState>
        favoriteStateObserver = mock(Observer::class.java) as Observer<Boolean>
        errorObserver = mock(Observer::class.java) as Observer<Throwable>
        movieDetailsViewModel.viewState.observeForever(viewObserver)
        movieDetailsViewModel.errorState.observeForever(errorObserver)
        movieDetailsViewModel.favoriteState.observeForever(favoriteStateObserver)
    }

    @Test
    @UiThreadTest
    fun showsCorrectDetailsAndFavoriteState() {
        val movieEntity = DomainTestUtils.getTestMovieEntity(testMovieId)
        `when`(moviesRepository.getMovie(testMovieId)).thenReturn(Observable.just(
                Optional.of(movieEntity)
        ))
        `when`(moviesCache.get(testMovieId)).thenReturn(Observable.just(Optional.of(movieEntity)))

        movieDetailsViewModel.getMovieDetails()

        val video = movieEntityMovieMapper.mapFrom(movieEntity)
        val expectedDetailsViewState = MovieDetailsViewState(
                isLoading = false,
                title = video.title,
                overview = video.details?.overview,
                videos = video.details?.videos,
                homepage = video.details?.homepage,
                releaseDate = video.releaseDate,
                backdropUrl = video.backdropPath,
                votesAverage = video.voteAverage,
                genres = video.details?.genres)

        verify(viewObserver).onChanged(expectedDetailsViewState)
        verify(favoriteStateObserver).onChanged(true)
        verifyZeroInteractions(errorObserver)
    }

    @Test
    @UiThreadTest
    fun showsErrorWhenFailsToGetMovieFromRepository() {
        val movieEntity = DomainTestUtils.getTestMovieEntity(testMovieId)
        val throwable = Throwable("ERROR!")

        `when`(moviesRepository.getMovie(testMovieId)).thenReturn(Observable.error(throwable))
        `when`(moviesCache.get(testMovieId)).thenReturn(Observable.just(Optional.of(movieEntity)))

        movieDetailsViewModel.getMovieDetails()

        verify(errorObserver).onChanged(throwable)
        verifyZeroInteractions(favoriteStateObserver)
    }

    @Test
    @UiThreadTest
    fun showsErrorWhenFailsToGetFavoriteState() {
        val movieEntity = DomainTestUtils.getTestMovieEntity(testMovieId)

        `when`(moviesRepository.getMovie(testMovieId)).thenReturn(Observable.just(Optional.empty()))
        `when`(moviesCache.get(testMovieId)).thenReturn(Observable.just(Optional.of(movieEntity)))

        movieDetailsViewModel.getMovieDetails()

        verify(errorObserver).onChanged(any(Throwable::class.java))
    }

    @Test
    @UiThreadTest
    fun showsErrorWhenGetMovieFromRepositoryReturnsEmptyOptional() {
        val movieEntity = DomainTestUtils.getTestMovieEntity(testMovieId)
        val throwable = Throwable("ERROR!")

        `when`(moviesRepository.getMovie(testMovieId)).thenReturn(Observable.just(Optional.of(movieEntity)))
        `when`(moviesCache.get(testMovieId)).thenReturn(Observable.error(throwable))

        movieDetailsViewModel.getMovieDetails()

        verify(errorObserver).onChanged(throwable)
        verifyZeroInteractions(favoriteStateObserver)
    }

    @Test
    @UiThreadTest
    fun favoriteStateChangesAsExpected() {
        val movieEntity = DomainTestUtils.getTestMovieEntity(testMovieId)
        `when`(moviesRepository.getMovie(testMovieId)).thenReturn(Observable.just(
                Optional.of(movieEntity)
        ))

        `when`(moviesCache.get(testMovieId)).thenReturn(Observable.just(Optional.of(movieEntity)))

        movieDetailsViewModel.getMovieDetails()
        verify(favoriteStateObserver).onChanged(true)

        movieDetailsViewModel.favoriteButtonClicked()
        verify(favoriteStateObserver).onChanged(false)

        movieDetailsViewModel.favoriteButtonClicked()
        verify(favoriteStateObserver).onChanged(true)

        verifyZeroInteractions(errorObserver)
    }


}