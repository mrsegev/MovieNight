package com.yossisegev.movienight.upcomingmovies

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.yossisegev.domain.common.Mapper
import com.yossisegev.domain.entities.MovieEntity
import com.yossisegev.domain.usecases.GetUpcomingMovies
import com.yossisegev.movienight.entities.Movie

class UpcomingMoviesVMFactory(private val useCase: GetUpcomingMovies,
                              private val mapper: Mapper<MovieEntity, Movie>) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UpcomingMoviesViewModel(useCase, mapper) as T
    }
}