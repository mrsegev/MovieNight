package com.yossisegev.domain

import com.yossisegev.domain.entities.MovieEntity
import com.yossisegev.domain.entities.Optional
import io.reactivex.Observable

/**
 * Created by Yossi Segev on 11/11/2017.
 */
interface MoviesDataStore {

    fun getMovieById(movieId: Int): Observable<Optional<MovieEntity>>
    fun getMovies(): Observable<List<MovieEntity>>
    fun getUpcomingMovies(): Observable<List<MovieEntity>>
    fun search(query: String): Observable<List<MovieEntity>>
}