package com.yossisegev.movienight

import com.yossisegev.domain.entities.*
import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * Created by Yossi Segev on 20/02/2018.
 */

class MovieEntityMovieMapperTests {

    @Test
    fun testMappingMovieEntityToMovieReturnsExpectedResult() {

        val videos = (0..4).map {
            VideoEntity(
                    id = "ID_$it",
                    name = "Video$it",
                    youtubeKey = "Key$it"
            )
        }

        val genres = (0..4).map {
            GenreEntity(
                    id = it,
                    name = "Genre$it"
            )
        }

        val reviews = (0..4).map {
            ReviewEntity(
                    id = "ID_$it",
                    author = "Author$it",
                    content = "Content$it"
            )
        }

        val movieEntity = MovieEntity(
                id = 1,
                title = "MovieData",
                backdropPath = "movieData_backdrop",
                originalLanguage = "movieData_lan",
                overview = "movieData_overview",
                posterPath = "movieData_poster",
                originalTitle = "Original title of MovieData",
                releaseDate = "1970-1-1",
                adult = true,
                popularity = 10.0,
                voteAverage = 7.0,
                video = false,
                voteCount = 100
        )
        val movieDetailsEntity = MovieDetailsEntity(
                overview = "movieData_overview",
                budget = 6,
                homepage = "homepage_url",
                imdbId = "imdb_id",
                revenue = 80,
                runtime = 60,
                tagline = "movie_tag_line",
                videos = videos,
                reviews = reviews,
                genres = genres
        )

        movieEntity.details = movieDetailsEntity
        val mapper = MovieEntityMovieMapper()
        val movie = mapper.mapFrom(movieEntity)

        assertEquals(movieEntity.id, movie.id)
        assertEquals(movieEntity.title, movie.title)
        assertEquals(movieEntity.originalTitle, movie.originalTitle)
        assertEquals(movieEntity.adult, movie.adult)
        assertEquals(movie.backdropPath, MovieEntityMovieMapper.backdropBaseUrl + movieEntity.backdropPath)
        assertEquals(movieEntity.releaseDate, movie.releaseDate)
        assertEquals(movieEntity.popularity, movie.popularity, 0.0)
        assertEquals(movieEntity.voteAverage, movie.voteAverage, 0.0)
        assertEquals(movieEntity.voteCount, movie.voteCount)
        assertEquals(movieEntity.video, movie.video)
        assertEquals(movie.posterPath, MovieEntityMovieMapper.posterBaseUrl + movieEntity.posterPath)
        assertEquals(movieEntity.originalLanguage, movie.originalLanguage)

        assertEquals(movieEntity.details?.videos?.size, movie.details!!.videos!!.size)
        assertEquals(movie.details!!.videos!![0].url, MovieEntityMovieMapper.youTubeBaseUrl + movieEntity.details!!.videos!![0].youtubeKey)
        assertEquals(movieEntity.details?.videos?.get(0)?.id, movie.details!!.videos!![0].id)
        assertEquals(movieEntity.details?.videos?.get(0)?.name, movie.details!!.videos!![0].name)

        assertEquals(movieEntity.details?.reviews?.size, movie.details!!.reviews!!.size)
        assertEquals(movieEntity.details?.reviews?.get(0)?.id, movie.details!!.reviews!![0].id)
        assertEquals(movieEntity.details?.reviews?.get(0)?.author, movie.details!!.reviews!![0].author)
        assertEquals(movieEntity.details?.reviews?.get(0)?.content, movie.details!!.reviews!![0].content)

        assertEquals(movieEntity.details?.genres?.size, movie.details!!.genres!!.size)
        assertEquals(movieEntity.details?.genres?.get(0)?.name, movie.details!!.genres!![0])

        assertEquals(movieEntity.details?.tagline, movie.details!!.tagline)
        assertEquals(movieEntity.details?.runtime, movie.details!!.runtime)
        assertEquals(movieEntity.details?.revenue, movie.details!!.revenue)
        assertEquals(movieEntity.details?.imdbId, movie.details!!.imdbId)
        assertEquals(movieEntity.details?.homepage, movie.details!!.homepage)
        assertEquals(movieEntity.details?.budget, movie.details!!.budget)
        assertEquals(movieEntity.details?.overview, movie.details!!.overview)
        assertEquals(movieEntity.details?.overview, movie.overview)
    }
}