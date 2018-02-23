package com.yossisegev.movienight.common

import android.widget.ImageView

/**
 * Created by Yossi Segev on 16/11/2017.
 */
interface ImageLoader {
    fun load(url: String, imageView: ImageView, callback: (Boolean) -> Unit)
    fun load(url: String, imageView: ImageView, fadeEffect: Boolean = true)
}

