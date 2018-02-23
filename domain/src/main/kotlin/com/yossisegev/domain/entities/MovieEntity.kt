package com.yossisegev.domain.entities

/**
 * Created by Yossi Segev on 11/11/2017.
 */
data class MovieEntity(

        var id: Int = 0,
        var voteCount: Int = 0,
        var video: Boolean = false,
        var voteAverage: Double = 0.0,
        var popularity: Double = 0.0,
        var adult: Boolean = false,
        var details: MovieDetailsEntity? = null,
        var title: String,
        var posterPath: String?,
        var originalLanguage: String,
        var originalTitle: String,
        var backdropPath: String?,
        var releaseDate: String,
        var overview: String? = null
)