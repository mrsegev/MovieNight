package com.yossisegev.movienight.di.modules

import android.content.Context
import com.squareup.picasso.Picasso
import com.yossisegev.movienight.common.ImageLoader
import com.yossisegev.movienight.common.PicassoImageLoader
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Yossi Segev on 11/11/2017.
 */

@Module
class AppModule constructor(context: Context){

    private val appContext = context.applicationContext

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return appContext
    }

    @Singleton
    @Provides
    fun provideImageLoader(context: Context) : ImageLoader {
        return PicassoImageLoader(Picasso.with(context))
    }
}