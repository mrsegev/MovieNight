package com.yossisegev.movienight.di.favorites

import com.yossisegev.movienight.details.MovieDetailsActivity
import com.yossisegev.movienight.favorites.FavoriteMoviesFragment
import dagger.Subcomponent

/**
 * Created by Yossi Segev on 23/02/2018.
 */
@FavoritesScope
@Subcomponent(modules = [FavoriteModule::class])
interface FavoritesSubComponent {
    fun inject(favoriteMoviesFragment: FavoriteMoviesFragment)
}