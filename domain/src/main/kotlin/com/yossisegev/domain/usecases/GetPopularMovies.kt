package com.yossisegev.domain.usecases

import com.yossisegev.domain.entities.MovieEntity
import com.yossisegev.domain.MoviesRepository
import com.yossisegev.domain.common.Transformer
import io.reactivex.Observable

/**
 * Created by Yossi Segev on 11/11/2017.
 */
open class GetPopularMovies(transformer: Transformer<List<MovieEntity>>,
                            private val moviesRepository: MoviesRepository) : UseCase<List<MovieEntity>>(transformer) {
    override fun createObservable(data: Map<String, Any>?): Observable<List<MovieEntity>> {
        return moviesRepository.getMovies()
    }

}