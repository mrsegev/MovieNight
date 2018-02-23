package com.yossisegev.data.db

import com.yossisegev.data.entities.MovieData
import com.yossisegev.domain.Mapper
import com.yossisegev.domain.MoviesCache
import com.yossisegev.domain.entities.MovieEntity
import com.yossisegev.domain.entities.Optional
import io.reactivex.Observable

/**
 * Created by Yossi Segev on 22/01/2018.
 */
class RoomFavoritesMovieCache(database: MoviesDatabase,
                              private val entityToDataMapper: Mapper<MovieEntity, MovieData>,
                              private val dataToEntityMapper: Mapper<MovieData, MovieEntity>) : MoviesCache {
    private val dao: MoviesDao = database.getMoviesDao()

    override fun clear() {
        dao.clear()
    }

    override fun save(movieEntity: MovieEntity) {
        dao.saveMovie(entityToDataMapper.mapFrom(movieEntity))
    }

    override fun remove(movieEntity: MovieEntity) {
        dao.removeMovie(entityToDataMapper.mapFrom(movieEntity))
    }

    override fun saveAll(movieEntities: List<MovieEntity>) {
        dao.saveAllMovies(movieEntities.map { entityToDataMapper.mapFrom(it) })
    }

    override fun getAll(): Observable<List<MovieEntity>> {
        return Observable.fromCallable { dao.getFavorites().map { dataToEntityMapper.mapFrom(it) } }
    }

    override fun get(movieId: Int): Observable<Optional<MovieEntity>> {

        return Observable.fromCallable {
            val movieData = dao.get(movieId)
            movieData?.let {
                Optional.of(dataToEntityMapper.mapFrom(it))
            } ?: Optional.empty()
        }
    }

    override fun isEmpty(): Boolean {
        return dao.getFavorites().isEmpty()
    }

    override fun search(query: String): Observable<List<MovieEntity>> {
        val searchQuery = "%$query%"
        return Observable.fromCallable {
            dao.search(searchQuery).map { dataToEntityMapper.mapFrom(it) }
        }
    }
}