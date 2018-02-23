package com.yossisegev.movienight.search

import com.yossisegev.movienight.entities.Movie

/**
 * Created by Yossi Segev on 11/02/2018.
 */
data class SearchViewState(
        val isLoading: Boolean = false,
        val movies: List<Movie>? = null,
        val lastSearchedQuery: String? = null,
        val showNoResultsMessage: Boolean = false
)