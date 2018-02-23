package com.yossisegev.data

import com.yossisegev.data.api.ReviewsResult
import com.yossisegev.data.api.VideoResult
import com.yossisegev.data.entities.DetailsData
import com.yossisegev.data.entities.GenreData
import com.yossisegev.data.entities.ReviewData
import com.yossisegev.data.entities.VideoData
import com.yossisegev.data.mappers.DetailsDataMovieEntityMapper
import com.yossisegev.data.mappers.MovieDataEntityMapper
import com.yossisegev.data.mappers.MovieEntityDataMapper
import com.yossisegev.data.utils.TestsUtils.Companion.getMockedMovieData
import com.yossisegev.domain.common.DomainTestUtils
import com.yossisegev.domain.entities.VideoEntity
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by Yossi Segev on 20/01/2018.
 */
class MappersTests {

    @Test
    fun testMappingMovieDataToMovieEntityReturnsExpectedResult() {

        val movieData = getMockedMovieData(40)
        val mapper = MovieDataEntityMapper()
        val movieEntity = mapper.mapFrom(movieData)

        assertEquals(movieEntity.id, movieData.id)
        assertEquals(movieEntity.title, movieData.title)
        assertEquals(movieEntity.originalTitle, movieData.originalTitle)
        assertEquals(movieEntity.adult, movieData.adult)
        assertEquals(movieEntity.backdropPath, movieData.backdropPath)
        assertEquals(movieEntity.releaseDate, movieData.releaseDate)
        assertEquals(movieEntity.popularity, movieData.popularity, 0.0)
        assertEquals(movieEntity.voteAverage, movieData.voteAverage, 0.0)
        assertEquals(movieEntity.voteCount, movieData.voteCount)
        assertEquals(movieEntity.posterPath, movieData.posterPath)
        assertEquals(movieEntity.originalLanguage, movieData.originalLanguage)
        assertEquals(movieEntity.overview, movieData.overview)
    }

    @Test
    fun testMappingDetailDataToMovieEntityReturnsExpectedResult() {

        val videos = (0..6).map {
            VideoData(
                    id = "ID_$it",
                    name = "Video$it",
                    site = if (it == 4) "Not YouTube" else VideoEntity.SOURCE_YOUTUBE,
                    type = if (it == 2) "Not a trailer" else VideoEntity.TYPE_TRAILER,
                    key = "Key$it"
            )
        }
        val videoResult = VideoResult()
        videoResult.results = videos

        val genres = (0..4).map {
            GenreData(
                    id = it,
                    name = "Genre$it"
            )
        }

        val reviews = (0..4).map {
            ReviewData(
                    id = "ID_$it",
                    author = "Author$it",
                    content = "Content$it"
            )
        }
        val reviewsResult = ReviewsResult()
        reviewsResult.results = reviews


        val detailsData = DetailsData(
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
                voteCount = 100,
                budget = 6,
                homepage = "homepage_url",
                imdbId = "imdb_id",
                revenue = 80,
                runtime = 60,
                tagline = "movie_tag_line"
        )

        detailsData.videos = videoResult
        detailsData.genres = genres
        detailsData.reviews = reviewsResult

        val mapper = DetailsDataMovieEntityMapper()
        val movieEntity = mapper.mapFrom(detailsData)

        assertEquals(movieEntity.id, detailsData.id)
        assertEquals(movieEntity.title, detailsData.title)
        assertEquals(movieEntity.originalTitle, detailsData.originalTitle)
        assertEquals(movieEntity.adult, detailsData.adult)
        assertEquals(movieEntity.backdropPath, detailsData.backdropPath)
        assertEquals(movieEntity.releaseDate, detailsData.releaseDate)
        assertEquals(movieEntity.popularity, detailsData.popularity, 0.0)
        assertEquals(movieEntity.voteAverage, detailsData.voteAverage, 0.0)
        assertEquals(movieEntity.voteCount, detailsData.voteCount)
        assertEquals(movieEntity.video, detailsData.video)
        assertEquals(movieEntity.posterPath, detailsData.posterPath)
        assertEquals(movieEntity.originalLanguage, detailsData.originalLanguage)
        assertEquals(movieEntity.overview, detailsData.overview)

        assertEquals(movieEntity.details?.videos?.size, detailsData.videos!!.results!!.size - 2) // ignoring non youtube/trailer
        assertEquals(movieEntity.details?.videos?.get(0)?.youtubeKey, detailsData.videos!!.results!![0].key)
        assertEquals(movieEntity.details?.videos?.get(0)?.id, detailsData.videos!!.results!![0].id)
        assertEquals(movieEntity.details?.videos?.get(0)?.name, detailsData.videos!!.results!![0].name)

        assertEquals(movieEntity.details?.reviews?.size, detailsData.reviews!!.results!!.size)
        assertEquals(movieEntity.details?.reviews?.get(0)?.id, detailsData.reviews!!.results!![0].id)
        assertEquals(movieEntity.details?.reviews?.get(0)?.author, detailsData.reviews!!.results!![0].author)
        assertEquals(movieEntity.details?.reviews?.get(0)?.content, detailsData.reviews!!.results!![0].content)

        assertEquals(movieEntity.details?.genres?.size, detailsData.genres!!.size)
        assertEquals(movieEntity.details?.genres?.get(0)?.id, detailsData.genres!![0].id)
        assertEquals(movieEntity.details?.genres?.get(0)?.name, detailsData.genres!![0].name)

        assertEquals(movieEntity.details?.tagline, detailsData.tagline)
        assertEquals(movieEntity.details?.runtime, detailsData.runtime)
        assertEquals(movieEntity.details?.revenue, detailsData.revenue)
        assertEquals(movieEntity.details?.imdbId, detailsData.imdbId)
        assertEquals(movieEntity.details?.homepage, detailsData.homepage)
        assertEquals(movieEntity.details?.budget, detailsData.budget)
        assertEquals(movieEntity.details?.overview, detailsData.overview)
    }

    @Test
    fun testMappingMovieEntityToMovieReturnsExpectedResult() {

        val movieEntity = DomainTestUtils.getTestMovieEntity(2)
        val movieEntityDataMapper = MovieEntityDataMapper()
        val movieData = movieEntityDataMapper.mapFrom(movieEntity)

        assertEquals(movieEntity.id, movieData.id)
        assertEquals(movieEntity.title, movieData.title)
        assertEquals(movieEntity.originalTitle, movieData.originalTitle)
        assertEquals(movieEntity.adult, movieData.adult)
        assertEquals(movieEntity.backdropPath, movieData.backdropPath)
        assertEquals(movieEntity.releaseDate, movieData.releaseDate)
        assertEquals(movieEntity.popularity, movieData.popularity, 0.0)
        assertEquals(movieEntity.voteAverage, movieData.voteAverage, 0.0)
        assertEquals(movieEntity.voteCount, movieData.voteCount)
        assertEquals(movieEntity.posterPath, movieData.posterPath)
        assertEquals(movieEntity.originalLanguage, movieData.originalLanguage)
        assertEquals(movieEntity.overview, movieData.overview)
    }
}