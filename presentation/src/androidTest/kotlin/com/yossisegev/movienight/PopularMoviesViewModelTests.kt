package com.yossisegev.movienight

import android.arch.lifecycle.Observer
import android.support.test.annotation.UiThreadTest
import android.support.test.runner.AndroidJUnit4
import com.yossisegev.domain.MoviesRepository
import com.yossisegev.domain.common.DomainTestUtils
import com.yossisegev.domain.common.TestTransformer
import com.yossisegev.domain.usecases.GetPopularMovies
import com.yossisegev.movienight.popularmovies.PopularMoviesViewModel
import com.yossisegev.movienight.popularmovies.PopularMoviesViewState
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.*

@Suppress("UNCHECKED_CAST")
@RunWith(AndroidJUnit4::class)
class PopularMoviesViewModelTests {

    private val movieEntityMovieMapper = MovieEntityMovieMapper()
    private lateinit var popularMoviesViewModel: PopularMoviesViewModel
    private lateinit var moviesRepository: MoviesRepository
    private lateinit var viewObserver: Observer<PopularMoviesViewState>
    private lateinit var errorObserver: Observer<Throwable?>

    @Before
    @UiThreadTest
    fun before() {
        moviesRepository = Mockito.mock(MoviesRepository::class.java)
        val getPopularMoviesUseCase = GetPopularMovies(TestTransformer(), moviesRepository)
        popularMoviesViewModel = PopularMoviesViewModel(getPopularMoviesUseCase, movieEntityMovieMapper)
        viewObserver = mock(Observer::class.java) as Observer<PopularMoviesViewState>
        errorObserver = mock(Observer::class.java) as Observer<Throwable?>
        popularMoviesViewModel.viewState.observeForever(viewObserver)
        popularMoviesViewModel.errorState.observeForever(errorObserver)
    }

    @Test
    @UiThreadTest
    fun testInitialViewStateShowsLoading() {
        verify(viewObserver).onChanged(PopularMoviesViewState(showLoading = true, movies = null))
        verifyZeroInteractions(viewObserver)
    }

    @Test
    @UiThreadTest
    fun testShowingMoviesAsExpectedAndStopsLoading() {
        val movieEntities = DomainTestUtils.generateMovieEntityList()
        `when`(moviesRepository.getMovies()).thenReturn(Observable.just(movieEntities))
        popularMoviesViewModel.getPopularMovies()
        val movies = movieEntities.map { movieEntityMovieMapper.mapFrom(it) }

        verify(viewObserver).onChanged(PopularMoviesViewState(showLoading = false, movies = movies))
        verify(errorObserver).onChanged(null)
    }

    @Test
    @UiThreadTest
    fun testShowingErrorMessageWhenNeeded() {
        val throwable = Throwable("ERROR!")
        `when`(moviesRepository.getMovies()).thenReturn(Observable.error(throwable))
        popularMoviesViewModel.getPopularMovies()

        verify(viewObserver).onChanged(PopularMoviesViewState(showLoading = false, movies = null))
        verify(errorObserver).onChanged(throwable)
    }
}
