package com.yossisegev.data.repositories

import com.yossisegev.data.api.Api
import com.yossisegev.data.entities.DetailsData
import com.yossisegev.data.entities.MovieData
import com.yossisegev.data.mappers.DetailsDataMovieEntityMapper
import com.yossisegev.data.mappers.MovieDataEntityMapper
import com.yossisegev.data.mappers.MovieEntityDataMapper
import com.yossisegev.domain.common.Mapper
import com.yossisegev.domain.MoviesDataStore
import com.yossisegev.domain.entities.MovieDetailsEntity
import com.yossisegev.domain.entities.MovieEntity
import com.yossisegev.domain.entities.Optional
import io.reactivex.Observable

/**
 * Created by Yossi Segev on 11/11/2017.
 */
class RemoteMoviesDataStore(private val api: Api) : MoviesDataStore {

    private val movieDataMapper = MovieDataEntityMapper()
    private val detailedDataMapper = DetailsDataMovieEntityMapper()

    override fun search(query: String): Observable<List<MovieEntity>> {
        return api.searchMovies(query).map { results ->
            results.movies.map { movieDataMapper.mapFrom(it) }
        }
    }

    override fun getMovies(): Observable<List<MovieEntity>> {
        return api.getPopularMovies().map { results ->
            results.movies.map { movieDataMapper.mapFrom(it) }
        }
    }

    override fun getMovieById(movieId: Int): Observable<Optional<MovieEntity>> {
        return api.getMovieDetails(movieId).flatMap { detailedData ->
            Observable.just(Optional.of(detailedDataMapper.mapFrom(detailedData)))
        }
    }
}