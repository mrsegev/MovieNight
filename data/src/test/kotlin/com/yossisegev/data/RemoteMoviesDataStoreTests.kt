package com.yossisegev.data

/**
 * Created by Yossi Segev on 19/01/2018.
 */
import com.yossisegev.data.api.Api
import com.yossisegev.data.api.MovieListResult
import com.yossisegev.data.entities.DetailsData
import com.yossisegev.data.entities.MovieData
import com.yossisegev.data.mappers.DetailsDataMovieEntityMapper
import com.yossisegev.data.mappers.MovieDataEntityMapper
import com.yossisegev.data.repositories.RemoteMoviesDataStore
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@Suppress("IllegalIdentifier")
class RemoteMoviesDataStoreTests {

    private lateinit var api: Api
    private lateinit var remoteMoviesDataStore: RemoteMoviesDataStore

    @Before
    fun before() {
        api = mock(Api::class.java)
        remoteMoviesDataStore = RemoteMoviesDataStore(api)
    }

    @Test
    fun testWhenRequestingPopularMoviesFromRemoteReturnExpectedResult() {

        val popularMovies = (0..4).map {
            MovieData(
                    id = it,
                    title = "Movie$it",
                    backdropPath = "",
                    originalLanguage = "",
                    posterPath = "",
                    originalTitle = "",
                    releaseDate = ""
            )
        }

        val movieListResult = MovieListResult()
        movieListResult.movies = popularMovies
        movieListResult.page = 1
        `when`(api.getPopularMovies()).thenReturn(Observable.just(movieListResult))

        remoteMoviesDataStore.getMovies().test()
                .assertValue { list -> list.size == 5 && list[0].title == "Movie0" }
                .assertComplete()
    }

    @Test
    fun testWhenRequestingMovieFromRemoteReturnExpectedValue() {

        val movieDetailedData = DetailsData(
                id = 1,
                title = "Movie1",
                backdropPath = "",
                originalLanguage = "",
                overview = "",
                posterPath = "",
                originalTitle = "",
                releaseDate = ""
        )

        `when`(api.getMovieDetails(1)).thenReturn(Observable.just(movieDetailedData))
        remoteMoviesDataStore.getMovieById(1).test()
                .assertValue { optional ->
                    optional.hasValue() &&
                            optional.value?.title == "Movie1" && optional.value?.id == 1
                }
                .assertComplete()
    }

    @Test
    fun testSearchingMovieReturnsExpectedResults() {
        val searchResults = (0..2).map {
            MovieData(
                    id = it,
                    title = "Movie$it",
                    backdropPath = "",
                    originalLanguage = "",
                    posterPath = "",
                    originalTitle = "",
                    releaseDate = ""
            )
        }

        val movieListResult = MovieListResult()
        movieListResult.movies = searchResults

        `when`(api.searchMovies("test query")).thenReturn(Observable.just(movieListResult))
        remoteMoviesDataStore.search("test query").test()
                .assertValue { results -> results.size == 3 }
                .assertComplete()
    }
}