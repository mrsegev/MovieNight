package com.yossisegev.movienight.di.upcoming

import com.yossisegev.movienight.upcomingmovies.UpcomingMoviesFragment
import dagger.Subcomponent

@Subcomponent(modules = [UpcomingMoviesModule::class])
interface UpcomingSubComponent {

    fun inject(upcomingMoviesFragment: UpcomingMoviesFragment)
}