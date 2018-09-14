package com.yossisegev.domain.usecases

import com.yossisegev.domain.MoviesDataStore
import com.yossisegev.domain.MoviesRepository
import com.yossisegev.domain.common.Transformer
import com.yossisegev.domain.entities.MovieEntity
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

/**
 * Created by Yossi Segev on 11/02/2018.
 */
class SearchMovie(transformer: Transformer<List<MovieEntity>>,
                  private val moviesRepository: MoviesRepository) : UseCase<List<MovieEntity>>(transformer) {

    companion object {
        private const val PARAM_SEARCH_QUERY = "param:search_query"
    }

    fun search(query: String): Observable<List<MovieEntity>> {
        val data = HashMap<String, String>()
        data[PARAM_SEARCH_QUERY] = query
        return createObservable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<MovieEntity>> {
        val query = data?.get(PARAM_SEARCH_QUERY)
        query?.let {
            return moviesRepository.search(it as String)
        } ?: return Observable.just(emptyList())
    }

}