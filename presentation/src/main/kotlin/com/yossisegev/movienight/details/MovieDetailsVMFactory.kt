package com.yossisegev.movienight.details

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.yossisegev.domain.Mapper
import com.yossisegev.domain.entities.MovieEntity
import com.yossisegev.domain.entities.Optional
import com.yossisegev.domain.usecases.CheckFavoriteStatus
import com.yossisegev.domain.usecases.GetMovieDetails
import com.yossisegev.domain.usecases.RemoveFavoriteMovie
import com.yossisegev.domain.usecases.SaveFavoriteMovie
import com.yossisegev.movienight.entities.Movie
import io.reactivex.ObservableTransformer

/**
 * Created by Yossi Segev on 07/01/2018.
 */
class MovieDetailsVMFactory(
        private val getMovieDetails: GetMovieDetails,
        private val saveFavoriteMovie: SaveFavoriteMovie,
        private val removeFavoriteMovie: RemoveFavoriteMovie,
        private val checkFavoriteStatus: CheckFavoriteStatus,
        private val mapper: Mapper<MovieEntity, Movie>) : ViewModelProvider.Factory {

    var movieId: Int = -1

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailsViewModel(
                getMovieDetails,
                saveFavoriteMovie,
                removeFavoriteMovie,
                checkFavoriteStatus,
                mapper,
                movieId) as T //TODO: solve casting issue
    }

}