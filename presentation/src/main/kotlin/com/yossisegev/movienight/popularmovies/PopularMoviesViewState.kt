package com.yossisegev.movienight.popularmovies

import com.yossisegev.movienight.entities.Movie

/**
 * Created by Yossi Segev on 06/01/2018.
 */
data class PopularMoviesViewState(
        var showLoading: Boolean = true,
        var movies: List<Movie>? = null
)
