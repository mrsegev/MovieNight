package com.yossisegev.movienight.di.details

import com.yossisegev.domain.MoviesCache
import com.yossisegev.domain.MoviesRepository
import com.yossisegev.domain.usecases.CheckFavoriteStatus
import com.yossisegev.domain.usecases.GetMovieDetails
import com.yossisegev.domain.usecases.RemoveFavoriteMovie
import com.yossisegev.domain.usecases.SaveFavoriteMovie
import com.yossisegev.movienight.MovieEntityMovieMapper
import com.yossisegev.movienight.common.ASyncTransformer
import com.yossisegev.movienight.details.MovieDetailsVMFactory
import com.yossisegev.movienight.di.DI
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Created by Yossi Segev on 23/02/2018.
 */
@Module
class MovieDetailsModule {

    @Provides
    fun provideRemoveFavoriteMovie(@Named(DI.favoritesCache) moviesCache: MoviesCache): RemoveFavoriteMovie {
        return RemoveFavoriteMovie(ASyncTransformer(), moviesCache)
    }

    @Provides
    fun provideCheckFavoriteStatus(@Named(DI.favoritesCache) moviesCache: MoviesCache): CheckFavoriteStatus {
        return CheckFavoriteStatus(ASyncTransformer(), moviesCache)
    }

    @Provides
    fun provideSaveFavoriteMovieUseCase(@Named(DI.favoritesCache) moviesCache: MoviesCache): SaveFavoriteMovie {
        return SaveFavoriteMovie(ASyncTransformer(), moviesCache)
    }

    @Provides
    fun provideGetMovieDetailsUseCase(moviesRepository: MoviesRepository): GetMovieDetails {
        return GetMovieDetails(ASyncTransformer(), moviesRepository)
    }

    @Provides
    fun provideMovieDetailsVMFactory(getMovieDetails: GetMovieDetails,
                                     saveFavoriteMovie: SaveFavoriteMovie,
                                     removeFavoriteMovie: RemoveFavoriteMovie,
                                     checkFavoriteStatus: CheckFavoriteStatus,
                                     mapper: MovieEntityMovieMapper): MovieDetailsVMFactory {
        return MovieDetailsVMFactory(getMovieDetails, saveFavoriteMovie, removeFavoriteMovie, checkFavoriteStatus, mapper)
    }
}