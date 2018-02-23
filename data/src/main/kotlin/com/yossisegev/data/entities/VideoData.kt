package com.yossisegev.data.entities

/**
 * Created by Yossi Segev on 11/01/2018.
 */
data class VideoData(
        var id: String,
        var name: String,
        var key: String? = null,
        var site: String? = null,
        var type: String? = null
)