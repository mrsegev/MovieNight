package com.yossisegev.data.repositories

import com.yossisegev.data.api.Api
import com.yossisegev.data.entities.DetailsData
import com.yossisegev.data.entities.MovieData
import com.yossisegev.domain.common.Mapper
import com.yossisegev.domain.MoviesCache
import com.yossisegev.domain.MoviesDataStore
import com.yossisegev.domain.MoviesRepository
import com.yossisegev.domain.entities.MovieEntity
import com.yossisegev.domain.entities.Optional
import io.reactivex.Observable

/**
 * Created by Yossi Segev on 25/01/2018.
 */

class MoviesRepositoryImpl(val api: Api,
                           private val cache: MoviesCache) : MoviesRepository {

    private val memoryDataStore: MoviesDataStore
    private val remoteDataStore: MoviesDataStore

    init {
        memoryDataStore = CachedMoviesDataStore(cache)
        remoteDataStore = RemoteMoviesDataStore(api)
    }

    override fun getMovies(): Observable<List<MovieEntity>> {

        return cache.isEmpty().flatMap { empty ->
            if (!empty) {
                return@flatMap memoryDataStore.getMovies()
            }
            else {
                return@flatMap remoteDataStore.getMovies().doOnNext { cache.saveAll(it) }
            }
        }
    }

    override fun search(query: String): Observable<List<MovieEntity>> {
        return remoteDataStore.search(query)
    }

    override fun getMovie(movieId: Int): Observable<Optional<MovieEntity>> {
        return remoteDataStore.getMovieById(movieId)
    }

}