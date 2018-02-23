package com.yossisegev.data.entities

/**
 * Created by Yossi Segev on 11/01/2018.
 */
data class ReviewData(
        var id: String,
        var author: String,
        var content: String? = null
)