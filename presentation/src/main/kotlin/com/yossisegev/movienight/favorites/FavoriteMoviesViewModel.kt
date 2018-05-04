package com.yossisegev.movienight.favorites

import android.arch.lifecycle.MutableLiveData
import com.yossisegev.domain.common.Mapper
import com.yossisegev.domain.entities.MovieEntity
import com.yossisegev.domain.usecases.GetFavoriteMovies
import com.yossisegev.movienight.common.BaseViewModel
import com.yossisegev.movienight.common.SingleLiveEvent
import com.yossisegev.movienight.entities.Movie

/**
 * Created by Yossi Segev on 09/02/2018.
 */
class FavoriteMoviesViewModel(private val getFavoriteMovies: GetFavoriteMovies,
                              private val movieEntityMovieMapper: Mapper<MovieEntity, Movie>) : BaseViewModel() {

    var viewState: MutableLiveData<FavoritesMoviesViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        val viewState = FavoritesMoviesViewState()
        this.viewState.value = viewState
    }

    fun getFavorites() {
        getFavoriteMovies.observable()
                .flatMap { movieEntityMovieMapper.observable(it) }
                .subscribe({ movies ->
                    val newViewState = viewState.value?.copy(
                            isEmpty = movies.isEmpty(),
                            isLoading = false,
                            movies = movies)
                    viewState.value = newViewState
                    errorState.value = null

                }, {
                    viewState.value = viewState.value?.copy(isLoading = false, isEmpty = false)
                    errorState.value = it

                })
    }

}