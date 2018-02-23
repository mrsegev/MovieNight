package com.yossisegev.movienight.di.search

import com.yossisegev.movienight.search.SearchFragment
import dagger.Subcomponent

/**
 * Created by Yossi Segev on 23/02/2018.
 */
@SearchScope
@Subcomponent(modules = [SearchMoviesModule::class])
interface SearchSubComponent {
    fun inject(searchFragment: SearchFragment)
}