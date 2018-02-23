package com.yossisegev.domain.entities

/**
 * Created by Yossi Segev on 26/01/2018.
 */
class Optional<out T>(val value: T? = null) {

    companion object {

        fun <T> of(value: T?): Optional<T> {
            return Optional(value)
        }

        fun <T> empty(): Optional<T> {
            return Optional()
        }
    }

    fun hasValue(): Boolean {
        return value != null
    }

}