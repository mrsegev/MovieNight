package com.yossisegev.movienight.upcomingmovies

import android.arch.lifecycle.MutableLiveData
import com.yossisegev.domain.common.Mapper
import com.yossisegev.domain.entities.MovieEntity
import com.yossisegev.domain.usecases.GetUpcomingMovies
import com.yossisegev.movienight.common.BaseViewModel
import com.yossisegev.movienight.common.SingleLiveEvent
import com.yossisegev.movienight.entities.Movie

class UpcomingMoviesViewModel(private val getUpcomingMovies: GetUpcomingMovies,
                              private val movieEntityMovieMapper: Mapper<MovieEntity, Movie>) : BaseViewModel() {

    var viewState: MutableLiveData<UpcomingMoviesViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        viewState.value = UpcomingMoviesViewState()
    }

    fun getUpcomingMovies() {
        addDisposable(getUpcomingMovies.observable()
                .flatMap { movieEntityMovieMapper.observable(it) }
                .subscribe({ movies ->
                    viewState.value?.let {
                        val newState = this.viewState.value?.copy(showLoading = false, movies = movies)
                        this.viewState.value = newState
                        this.errorState.value = null
                    }
                }, {
                    viewState.value = viewState.value?.copy(showLoading = false)
                    errorState.value = it
                }))
    }
}