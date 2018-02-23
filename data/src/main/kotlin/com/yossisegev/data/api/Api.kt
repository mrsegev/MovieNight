package com.yossisegev.data.api

import com.yossisegev.data.entities.DetailsData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Yossi Segev on 11/11/2017.
 */
interface Api {

    @GET("movie/{id}?append_to_response=videos,reviews")
    fun getMovieDetails(@Path("id") movieId: Int): Observable<DetailsData>

    @GET("movie/popular") ///movie/now_playing
    fun getPopularMovies(): Observable<MovieListResult>

    @GET("search/movie")
    fun searchMovies(@Query("query") query: String): Observable<MovieListResult>

}