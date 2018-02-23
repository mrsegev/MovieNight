package com.yossisegev.domain.entities

/**
 * Created by Yossi Segev on 11/01/2018.
 */
data class Review(

        var id: String,
        var author: String,
        var content: String? = null
)