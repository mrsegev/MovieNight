package com.yossisegev.domain.common

import io.reactivex.ObservableTransformer

/**
 * Created by Yossi Segev on 20/02/2018.
 */
abstract class Transformer<T> : ObservableTransformer<T, T>