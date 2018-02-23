package com.yossisegev.movienight

import com.yossisegev.domain.Mapper
import com.yossisegev.domain.entities.Genre
import com.yossisegev.domain.entities.MovieEntity
import com.yossisegev.domain.entities.Review
import com.yossisegev.movienight.entities.Movie
import com.yossisegev.movienight.entities.MovieDetails
import com.yossisegev.movienight.entities.Video
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Yossi Segev on 11/11/2017.
 */

@Singleton
class MovieEntityMovieMapper @Inject constructor() : Mapper<MovieEntity, Movie>() {

    companion object {
        const val posterBaseUrl = "https://image.tmdb.org/t/p/w342"
        const val backdropBaseUrl = "https://image.tmdb.org/t/p/w780"
        const val youTubeBaseUrl = "https://www.youtube.com/watch?v="
    }


    override fun mapFrom(from: MovieEntity): Movie {
        val movie = Movie(
                id = from.id,
                voteCount = from.voteCount,
                video = from.video,
                voteAverage = from.voteAverage,
                title = from.title,
                popularity = from.popularity,
                originalLanguage = from.originalLanguage,
                posterPath = from.posterPath?.let { posterBaseUrl + from.posterPath },
                backdropPath = from.backdropPath?. let { backdropBaseUrl + from.backdropPath },
                originalTitle = from.originalTitle,
                adult = from.adult,
                releaseDate = from.releaseDate,
                overview = from.overview
        )

        val fromDetails = from.details ?: return movie

        val details = MovieDetails()
        details.belongsToCollection = fromDetails.belongsToCollection
        details.budget = fromDetails.budget
        details.homepage = fromDetails.homepage
        details.imdbId = fromDetails.imdbId
        details.overview = fromDetails.overview
        details.revenue = fromDetails.revenue
        details.runtime = fromDetails.runtime
        details.status = fromDetails.status
        details.tagline = fromDetails.tagline
        movie.details = details

        fromDetails.genres?.let {
            val genres = it.map { it.name }
            details.genres = genres
        }

        fromDetails.videos?.let {
            val videos = it.map { videoEntity ->
                return@map Video(
                        id = videoEntity.id,
                        name = videoEntity.name,
                        url = youTubeBaseUrl + videoEntity.youtubeKey
                )
            }
            details.videos = videos
        }

        fromDetails.reviews?.let {
            val reviews = it.map { reviewEntity ->
                return@map Review(
                        id = reviewEntity.id,
                        author = reviewEntity.author,
                        content = reviewEntity.content
                )
            }
            details.reviews = reviews
        }


        return movie
    }
}