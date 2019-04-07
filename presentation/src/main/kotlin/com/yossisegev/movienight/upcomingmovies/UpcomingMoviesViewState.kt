package com.yossisegev.movienight.upcomingmovies

import com.yossisegev.movienight.entities.Movie

data class UpcomingMoviesViewState(
        var showLoading: Boolean = true,
        var movies: List<Movie>? = null
)