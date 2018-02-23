package com.yossisegev.data.api

import com.google.gson.annotations.SerializedName
import com.yossisegev.data.entities.MovieData

/**
 * Created by Yossi Segev on 11/11/2017.
 */
class MovieListResult {

    var page: Int = 0
    @SerializedName("results")
    lateinit var movies: List<MovieData>
}