package com.yossisegev.movienight.di.search

import com.yossisegev.domain.MoviesRepository
import com.yossisegev.domain.usecases.SearchMovie
import com.yossisegev.movienight.MovieEntityMovieMapper
import com.yossisegev.movienight.common.ASyncTransformer
import com.yossisegev.movienight.search.SearchVMFactory
import dagger.Module
import dagger.Provides

/**
 * Created by Yossi Segev on 23/02/2018.
 */
@Module
class SearchMoviesModule {

    @Provides
    fun provideSearchMovieUseCase(moviesRepository: MoviesRepository): SearchMovie {
        return SearchMovie(ASyncTransformer(), moviesRepository)
    }

    @Provides
    fun provideSearchVMFactory(useCase: SearchMovie, mapper: MovieEntityMovieMapper): SearchVMFactory {
        return SearchVMFactory(useCase, mapper)
    }
}