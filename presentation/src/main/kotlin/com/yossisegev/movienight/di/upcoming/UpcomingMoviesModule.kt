package com.yossisegev.movienight.di.upcoming

import com.yossisegev.domain.MoviesRepository
import com.yossisegev.domain.usecases.GetUpcomingMovies
import com.yossisegev.movienight.MovieEntityMovieMapper
import com.yossisegev.movienight.common.ASyncTransformer
import com.yossisegev.movienight.upcomingmovies.UpcomingMoviesVMFactory
import dagger.Module
import dagger.Provides

@UpcomingScope
@Module
class UpcomingMoviesModule {

    @Provides
    fun provideGetUpcomingMoviesUseCase(moviesRepository: MoviesRepository): GetUpcomingMovies {
        return GetUpcomingMovies(ASyncTransformer(), moviesRepository)
    }

    @Provides
    fun provideUpcomingMoviesVMFactory(useCase: GetUpcomingMovies,
                                       mapper: MovieEntityMovieMapper): UpcomingMoviesVMFactory {
        return UpcomingMoviesVMFactory(useCase, mapper)
    }
}