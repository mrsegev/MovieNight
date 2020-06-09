package com.yossisegev.movienight.details

import android.arch.lifecycle.MutableLiveData
import com.yossisegev.domain.common.Mapper
import com.yossisegev.domain.entities.MovieEntity
import com.yossisegev.domain.usecases.CheckFavoriteStatus
import com.yossisegev.domain.usecases.GetMovieDetails
import com.yossisegev.domain.usecases.RemoveFavoriteMovie
import com.yossisegev.domain.usecases.SaveFavoriteMovie
import com.yossisegev.movienight.common.BaseViewModel
import com.yossisegev.movienight.common.SingleLiveEvent
import com.yossisegev.movienight.entities.Movie
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
 * Created by Yossi Segev on 07/01/2018.
 */
class MovieDetailsViewModel(private val getMovieDetails: GetMovieDetails,
                            private val saveFavoriteMovie: SaveFavoriteMovie,
                            private val removeFavoriteMovie: RemoveFavoriteMovie,
                            private val checkFavoriteStatus: CheckFavoriteStatus,
                            private val mapper: Mapper<MovieEntity, Movie>,
                            private val movieId: Int) : BaseViewModel() {

    lateinit var movieEntity: MovieEntity
    var viewState: MutableLiveData<MovieDetailsViewState> = MutableLiveData()
    var favoriteState: MutableLiveData<Boolean> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()

    init {
        viewState.value = MovieDetailsViewState(isLoading = true)
    }

    fun getMovieDetails() {
        addDisposable(
            getMovieDetails.getById(movieId)
                .map {
                    it.value?.let {
                        movieEntity = it
                        mapper.mapFrom(movieEntity)
                    } ?: run {
                        throw Throwable("Something went wrong :(")
                    }
                }
                .zipWith(checkFavoriteStatus.check(movieId), BiFunction<Movie, Boolean, Movie> { movie, isFavorite ->
                    movie.isFavorite = isFavorite
                    return@BiFunction movie
                })
                .subscribe(
                    { onMovieDetailsReceived(it) },
                    { errorState.value = it }
                )
        )
    }

    fun favoriteButtonClicked() {
        addDisposable(checkFavoriteStatus.check(movieId)
            .flatMap {
                when (it) {
                    true -> {
                        removeFavorite(movieEntity)
                    }
                    false -> {
                        saveFavorite(movieEntity)
                    }
                }
            }.subscribe({ isFavorite ->
                favoriteState.value = isFavorite
            }, {
                errorState.value = it
            }))
    }

    private fun onMovieDetailsReceived(movie: Movie) {
        val newViewState = viewState.value?.copy(
            isLoading = false,
            title = movie.originalTitle,
            videos = movie.details?.videos,
            homepage = movie.details?.homepage,
            overview = movie.details?.overview,
            releaseDate = movie.releaseDate,
            votesAverage = movie.voteAverage,
            backdropUrl = movie.backdropPath,
            genres = movie.details?.genres)

        viewState.value = newViewState
        favoriteState.value = movie.isFavorite
    }

    private fun saveFavorite(movieEntity: MovieEntity): Observable<Boolean> {
        return saveFavoriteMovie.save(movieEntity)
    }

    private fun removeFavorite(movieEntity: MovieEntity): Observable<Boolean> {
        return removeFavoriteMovie.remove(movieEntity)
    }
}