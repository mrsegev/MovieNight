package com.yossisegev.movienight.di.popular

import com.yossisegev.movienight.popularmovies.PopularMoviesFragment
import dagger.Subcomponent

/**
 * Created by Yossi Segev on 23/02/2018.
 */
@Subcomponent(modules = [PopularMoviesModule::class])
interface PopularSubComponent {
    fun inject(popularMoviesFragment: PopularMoviesFragment)
}