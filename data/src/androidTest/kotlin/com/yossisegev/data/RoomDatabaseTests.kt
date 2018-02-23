package com.yossisegev.data

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.yossisegev.data.db.MoviesDao
import com.yossisegev.data.db.MoviesDatabase
import com.yossisegev.data.utils.TestsUtils
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Yossi Segev on 19/02/2018.
 */
@RunWith(AndroidJUnit4::class)
class RoomDatabaseTests {

    private lateinit var database: MoviesDatabase
    private lateinit var moviesDao: MoviesDao

    @Before
    fun before() {
        database = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                MoviesDatabase::class.java).build()

        moviesDao = database.getMoviesDao()
    }

    @After
    fun after() {
        database.close()
    }

    @Test
    fun testSingleInsertion() {
        val movieData = TestsUtils.getMockedMovieData(21)
        moviesDao.saveMovie(movieData)
        val result = moviesDao.get(movieData.id)
        assertNotNull(result)
        assertEquals(result?.id?.toLong(), movieData.id.toLong())
    }

    @Test
    fun testMultipleInsertions() {
        val movieDataList = TestsUtils.generateMovieDataList()
        moviesDao.saveAllMovies(movieDataList)
        val movies = moviesDao.getFavorites()
        assertEquals(movies.size.toLong(), 5)
    }

    @Test
    fun testRemoval() {
        val movieData = TestsUtils.getMockedMovieData(22)
        moviesDao.saveMovie(movieData)
        val movies = moviesDao.getFavorites()
        assertEquals(movies.size.toLong(), 1)

        moviesDao.removeMovie(movieData)
        val movies2 = moviesDao.getFavorites()
        assertEquals(movies2.size.toLong(), 0)
    }

    @Test
    fun testAllDataIsSaved() {
        val movieData = TestsUtils.getMockedMovieData(432)
        moviesDao.saveMovie(movieData)
        val movies = moviesDao.getFavorites()
        val (id, voteCount, voteAverage, adult, popularity, title, posterPath, originalLanguage, originalTitle, backdropPath, releaseDate) = movies[0]
        assertEquals(id.toLong(), movieData.id.toLong())
        assertEquals(title, movieData.title)
        assertEquals(posterPath, movieData.posterPath)
        assertEquals(backdropPath, movieData.backdropPath)
        assertEquals(adult, movieData.adult)
        assertEquals(popularity, movieData.popularity, 0.0)
        assertEquals(originalLanguage, movieData.originalLanguage)
        assertEquals(releaseDate, movieData.releaseDate)
        assertEquals(voteAverage, movieData.voteAverage, 0.0)
        assertEquals(voteCount.toLong(), movieData.voteCount.toLong())
        assertEquals(originalTitle, movieData.originalTitle)
    }

    @Test
    fun testClearTable() {
        val movieDataList = TestsUtils.generateMovieDataList()
        moviesDao.saveAllMovies(movieDataList)
        val movies = moviesDao.getFavorites()
        assertEquals(movies.size.toLong(), 5)
        moviesDao.clear()
        assertTrue(moviesDao.getFavorites().isEmpty())
    }
    
    @Test
    fun testSearchingMovieReturnsExpectedResults() {
        val movieData1 = TestsUtils.getMockedMovieData(150, "Star wars")
        val movieData2 = TestsUtils.getMockedMovieData(151, "The Star")
        val movieData3 = TestsUtils.getMockedMovieData(152, "The Starcraft story")
        val movieData4 = TestsUtils.getMockedMovieData(153, "The hobbit")
        moviesDao.saveMovie(movieData1)
        moviesDao.saveMovie(movieData2)
        moviesDao.saveMovie(movieData3)
        moviesDao.saveMovie(movieData4)
        val results = moviesDao.search("%star%")
        assertTrue(results.size == 3)
    }
}
