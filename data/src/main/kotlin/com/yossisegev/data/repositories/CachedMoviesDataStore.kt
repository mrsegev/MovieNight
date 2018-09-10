package com.yossisegev.data.repositories

import com.yossisegev.domain.MoviesCache
import com.yossisegev.domain.MoviesDataStore
import com.yossisegev.domain.entities.MovieEntity
import com.yossisegev.domain.entities.Optional
import io.reactivex.Observable

/**
 * Created by Yossi Segev on 22/01/2018.
 */
class CachedMoviesDataStore(private val moviesCache: MoviesCache): MoviesDataStore {

    override fun search(query: String): Observable<List<MovieEntity>> {
        return moviesCache.search(query)
    }

    override fun getMovieById(movieId: Int): Observable<Optional<MovieEntity>> {
        return moviesCache.get(movieId)
    }

    override fun getMovies(): Observable<List<MovieEntity>> {
        return moviesCache.getAll()
    }

    fun isEmpty(): Observable<Boolean> {
        return moviesCache.isEmpty()
    }

    fun saveAll(movieEntities: List<MovieEntity>) {
        moviesCache.saveAll(movieEntities)
    }
}