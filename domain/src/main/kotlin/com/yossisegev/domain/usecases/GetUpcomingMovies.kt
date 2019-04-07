package com.yossisegev.domain.usecases

import com.yossisegev.domain.MoviesRepository
import com.yossisegev.domain.common.Transformer
import com.yossisegev.domain.entities.MovieEntity
import io.reactivex.Observable

open class GetUpcomingMovies(transformer: Transformer<List<MovieEntity>>,
                             private val moviesRepository: MoviesRepository) : UseCase<List<MovieEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<MovieEntity>> {
        return moviesRepository.getUpcomingMovies()
    }

}