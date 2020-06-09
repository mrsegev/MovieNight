package com.yossisegev.domain.usecases

import com.yossisegev.domain.common.Transformer
import io.reactivex.Observable

/**
 * Created by Yossi Segev on 11/11/2017.
 */
abstract class UseCase<T>(private val transformer: Transformer<T>) {

    abstract fun createObservable(data: Map<String, Any>? = null): Observable<T>

    fun observable(withData: Map<String, Any>? = null): Observable<T> {
        return createObservable(withData).compose(transformer)
    }
}