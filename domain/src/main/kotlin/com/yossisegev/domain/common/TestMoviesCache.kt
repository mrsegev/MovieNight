package com.yossisegev.domain.common

import com.yossisegev.domain.MoviesCache
import com.yossisegev.domain.entities.MovieEntity
import com.yossisegev.domain.entities.Optional
import io.reactivex.Observable

/**
 * Created by Yossi Segev on 13/11/2017.
 */
class TestMoviesCache : MoviesCache {

    private val movies: HashMap<Int, MovieEntity> = HashMap()

    override fun search(query: String): Observable<List<MovieEntity>> {
        return Observable.just(movies.values.toList())
    }

    override fun isEmpty(): Boolean {
        return movies.isEmpty()
    }

    override fun remove(movieEntity: MovieEntity) {
        movies.remove(movieEntity.id)
    }

    override fun clear() {
        movies.clear()
    }

    override fun save(movieEntity: MovieEntity) {
        movies[movieEntity.id] = movieEntity
    }

    override fun saveAll(movieEntities: List<MovieEntity>) {
        movieEntities.forEach { movieEntity -> this.movies[movieEntity.id] = movieEntity }
    }

    override fun getAll(): Observable<List<MovieEntity>> {
        return Observable.just(movies.values.toList())
    }

    override fun get(movieId: Int): Observable<Optional<MovieEntity>> {
        return Observable.just(Optional.of(movies[movieId]))
    }
}

