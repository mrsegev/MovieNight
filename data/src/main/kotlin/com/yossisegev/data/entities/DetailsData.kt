package com.yossisegev.data.entities

import com.google.gson.annotations.SerializedName
import com.yossisegev.data.api.ReviewsResult
import com.yossisegev.data.api.VideoResult

/**
 * Created by Yossi Segev on 07/01/2018.
 */
data class DetailsData(

        @SerializedName("adult")
        var adult: Boolean = false,

//    @SerializedName("belongs_to_collection")
//    var belongsToCollection: Any? = null

        @SerializedName("budget")
        var budget: Int? = null,

        @SerializedName("genres")
        var genres: List<GenreData>? = null,

        @SerializedName("videos")
        var videos: VideoResult? = null,

        @SerializedName("reviews")
        var reviews: ReviewsResult? = null,

        @SerializedName("homepage")
        var homepage: String? = null,

        @SerializedName("id")
        var id: Int = -1,

        @SerializedName("imdb_id")
        var imdbId: String? = null,

        @SerializedName("popularity")
        var popularity: Double = 0.0,

//    @SerializedName("production_companies")
//    @Expose
//    var productionCompanies: List<ProductionCompany>? = null

//    @SerializedName("production_countries")
//    @Expose
//    var productionCountries: List<ProductionCountry>? = null

        @SerializedName("revenue")
        var revenue: Int? = null,

        @SerializedName("runtime")
        var runtime: Int? = null,

//    @SerializedName("spoken_languages")
//    @Expose
//    var spokenLanguages: List<SpokenLanguage>? = null

//    @SerializedName("status")
//    var status: String? = null

        @SerializedName("tagline")
        var tagline: String? = null,

        @SerializedName("video")
        var video: Boolean = false,

        @SerializedName("vote_average")
        var voteAverage: Double = 0.0,

        @SerializedName("vote_count")
        var voteCount: Int = 0,

        @SerializedName("title")
        var title: String,

        @SerializedName("poster_path")
        var posterPath: String,

        @SerializedName("original_language")
        var originalLanguage: String,

        @SerializedName("original_title")
        var originalTitle: String,

        @SerializedName("backdrop_path")
        var backdropPath: String,

        @SerializedName("overview")
        var overview: String,

        @SerializedName("release_date")
        var releaseDate: String
)