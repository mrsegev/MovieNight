package com.yossisegev.movienight.di.popular

import com.yossisegev.domain.MoviesRepository
import com.yossisegev.domain.usecases.GetPopularMovies
import com.yossisegev.movienight.MovieEntityMovieMapper
import com.yossisegev.movienight.common.ASyncTransformer
import com.yossisegev.movienight.di.popular.PopularScope
import com.yossisegev.movienight.popularmovies.PopularMoviesVMFactory
import dagger.Module
import dagger.Provides

/**
 * Created by Yossi Segev on 23/02/2018.
 */
@PopularScope
@Module
class PopularMoviesModule {
    @Provides
    fun provideGetPopularMoviesUseCase(moviesRepository: MoviesRepository): GetPopularMovies {
        return GetPopularMovies(ASyncTransformer(), moviesRepository)
    }

    @Provides
    fun providePopularMoviesVMFactory(useCase: GetPopularMovies, mapper: MovieEntityMovieMapper): PopularMoviesVMFactory {
        return PopularMoviesVMFactory(useCase, mapper)
    }
}