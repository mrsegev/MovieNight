package com.yossisegev.data.repositories

import com.yossisegev.domain.MoviesRepository
import com.yossisegev.domain.entities.MovieEntity
import com.yossisegev.domain.entities.Optional
import io.reactivex.Observable

/**
 * Created by Yossi Segev on 25/01/2018.
 */

class MoviesRepositoryImpl(private val cachedDataStore: CachedMoviesDataStore,
                           private val remoteDataStore: RemoteMoviesDataStore) : MoviesRepository {

    override fun getMovies(): Observable<List<MovieEntity>> {

        return cachedDataStore.isEmpty().flatMap { empty ->
            if (!empty) {
                return@flatMap cachedDataStore.getMovies()
            }
            else {
                return@flatMap remoteDataStore.getMovies()
                                              .doOnNext { movies ->
                                                  cachedDataStore.saveAll(movies)
                                              }
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