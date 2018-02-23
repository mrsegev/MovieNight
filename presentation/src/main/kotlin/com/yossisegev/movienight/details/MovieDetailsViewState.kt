package com.yossisegev.movienight.details

import com.yossisegev.domain.entities.Genre
import com.yossisegev.movienight.entities.Video

/**
 * Created by Yossi Segev on 10/01/2018.
 */
data class MovieDetailsViewState(
        var isLoading: Boolean = true,
        var title: String? = null,
        var overview: String? = null,
        var videos: List<Video>? = null,
        var homepage: String? = null,
        var releaseDate: String? = null,
        var votesAverage: Double? = null,
        var backdropUrl: String? = null,
        var genres: List<String>? = null
)