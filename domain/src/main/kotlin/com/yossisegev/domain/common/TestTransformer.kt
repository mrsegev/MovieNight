package com.yossisegev.domain.common

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

/**
 * Created by Yossi Segev on 13/11/2017.
 */
class TestTransformer<T>: Transformer<T>() {
    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream
    }

}