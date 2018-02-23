package com.yossisegev.movienight.entities

import android.graphics.Movie

/**
 * Created by Yossi Segev on 14/11/2017.
 */
data class Movie (

    var id: Int = 0,
    var voteCount: Int = 0,
    var video: Boolean = false,
    var voteAverage: Double = 0.0,
    var title: String,
    var popularity: Double = 0.0,
    var posterPath: String? = null,
    var originalLanguage: String,
    var originalTitle: String,
    var backdropPath: String? = null,
    var adult: Boolean = false,
    var releaseDate: String,
    var details: MovieDetails? = null,
    var isFavorite: Boolean = false,
    var overview: String? = null) {

    fun containsVideos(): Boolean {
        return details?.videos != null && details?.videos?.isNotEmpty() ?: false
    }

    fun containsReviews(): Boolean {
        return details?.reviews != null && details?.reviews?.isNotEmpty() ?: false
    }

    fun containsGenres(): Boolean {
        return details?.genres != null && details?.genres?.isNotEmpty() ?: false
    }
}