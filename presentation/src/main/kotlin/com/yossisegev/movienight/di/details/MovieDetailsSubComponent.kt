package com.yossisegev.movienight.di.details

import com.yossisegev.movienight.details.MovieDetailsActivity
import dagger.Subcomponent

/**
 * Created by Yossi Segev on 23/02/2018.
 */
@DetailsScope
@Subcomponent(modules = [MovieDetailsModule::class])
interface MovieDetailsSubComponent {
    fun inject(movieDetailsActivity: MovieDetailsActivity)
}