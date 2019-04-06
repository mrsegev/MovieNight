package com.yossisegev.movienight.common

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import com.yossisegev.movienight.R
import com.yossisegev.movienight.di.DaggerMainComponent
import com.yossisegev.movienight.di.MainComponent
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

/**
 * Created by Yossi Segev on 11/11/2017.
 */
class App: Application() {

    private lateinit var mainComponent: MainComponent
    private var popularMoviesComponent: PopularSubComponent? = null
    private var favoriteMoviesComponent: FavoritesSubComponent? = null
    private var movieDetailsComponent: MovieDetailsSubComponent? = null
    private var searchMoviesComponent: SearchSubComponent? = null

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)

        initDependencies()
    }

    private fun initDependencies() {
        mainComponent = DaggerMainComponent.builder()
                .appModule(AppModule(applicationContext))
                .networkModule(NetworkModule(getString(R.string.api_base_url), getString(R.string.api_key)))
                .dataModule(DataModule())
                .build()

    }

    fun createPopularComponenet(): PopularSubComponent {
        popularMoviesComponent = mainComponent.plus(PopularMoviesModule())
        return popularMoviesComponent!!
    }
    fun releasePopularComponent() {
        popularMoviesComponent = null
    }

    fun createFavoritesComponent() : FavoritesSubComponent {
        favoriteMoviesComponent = mainComponent.plus(FavoriteModule())
        return favoriteMoviesComponent!!
    }
    fun releaseFavoritesComponent() {
        favoriteMoviesComponent = null
    }

    fun createDetailsComponent(): MovieDetailsSubComponent {
        movieDetailsComponent = mainComponent.plus(MovieDetailsModule())
        return movieDetailsComponent!!
    }
    fun releaseDetailsComponent() {
        movieDetailsComponent = null
    }

    fun createSearchComponent(): SearchSubComponent {
        searchMoviesComponent = mainComponent.plus(SearchMoviesModule())
        return searchMoviesComponent!!
    }
    fun releaseSearchComponent() {
        searchMoviesComponent = null
    }
}