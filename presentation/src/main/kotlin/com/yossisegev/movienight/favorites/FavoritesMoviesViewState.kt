package com.yossisegev.movienight.favorites

import com.yossisegev.movienight.entities.Movie

/**
 * Created by Yossi Segev on 09/02/2018.
 */
data class FavoritesMoviesViewState(
    val isLoading: Boolean = true,
    val isEmpty: Boolean = true,
    val movies: List<Movie>? = null
)