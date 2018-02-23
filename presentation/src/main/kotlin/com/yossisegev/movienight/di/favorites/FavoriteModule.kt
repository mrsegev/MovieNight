package com.yossisegev.movienight.di.favorites

import com.yossisegev.domain.MoviesCache
import com.yossisegev.domain.usecases.CheckFavoriteStatus
import com.yossisegev.domain.usecases.GetFavoriteMovies
import com.yossisegev.domain.usecases.RemoveFavoriteMovie
import com.yossisegev.domain.usecases.SaveFavoriteMovie
import com.yossisegev.movienight.MovieEntityMovieMapper
import com.yossisegev.movienight.common.ASyncTransformer
import com.yossisegev.movienight.di.DI
import com.yossisegev.movienight.favorites.FavoriteMoviesVMFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Created by Yossi Segev on 23/02/2018.
 */
@Module
class FavoriteModule {

    @Provides
    fun provideGetFavoriteMovies(@Named(DI.favoritesCache) moviesCache: MoviesCache): GetFavoriteMovies {
        return GetFavoriteMovies(ASyncTransformer(), moviesCache)
    }

    @Provides
    fun provideFavoriteMoviesVMFactory(getFavoriteMovies: GetFavoriteMovies,
                                       movieEntityMoveMapper: MovieEntityMovieMapper): FavoriteMoviesVMFactory {
        return FavoriteMoviesVMFactory(getFavoriteMovies, movieEntityMoveMapper)
    }
}