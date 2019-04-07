package com.yossisegev.movienight.di

import com.yossisegev.movienight.di.details.MovieDetailsModule
import com.yossisegev.movienight.di.details.MovieDetailsSubComponent
import com.yossisegev.movienight.di.favorites.FavoriteModule
import com.yossisegev.movienight.di.favorites.FavoritesSubComponent
import com.yossisegev.movienight.di.modules.AppModule
import com.yossisegev.movienight.di.modules.DataModule
import com.yossisegev.movienight.di.modules.NetworkModule
import com.yossisegev.movienight.di.popular.PopularMoviesModule
import com.yossisegev.movienight.di.popular.PopularSubComponent
import com.yossisegev.movienight.di.search.SearchMoviesModule
import com.yossisegev.movienight.di.search.SearchSubComponent
import com.yossisegev.movienight.di.upcoming.UpcomingMoviesModule
import com.yossisegev.movienight.di.upcoming.UpcomingSubComponent
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Yossi Segev on 11/11/2017.
 */
@Singleton
@Component(modules = [
    (AppModule::class),
    (NetworkModule::class),
    (DataModule::class)
])

interface MainComponent {
    fun plus(popularMoviesModule: PopularMoviesModule): PopularSubComponent
    fun plus(upcomingMoviesModule: UpcomingMoviesModule): UpcomingSubComponent
    fun plus(favoriteMoviesModule: FavoriteModule): FavoritesSubComponent
    fun plus(movieDetailsModule: MovieDetailsModule): MovieDetailsSubComponent
    fun plus(searchMoviesModule: SearchMoviesModule): SearchSubComponent
}