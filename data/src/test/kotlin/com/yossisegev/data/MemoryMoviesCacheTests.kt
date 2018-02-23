package com.yossisegev.data

/**
 * Created by Yossi Segev on 19/01/2018.
 */
import com.yossisegev.data.repositories.MemoryMoviesCache
import com.yossisegev.domain.common.DomainTestUtils.Companion.generateMovieEntityList
import com.yossisegev.domain.common.DomainTestUtils.Companion.getTestMovieEntity
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MemoryMoviesCacheTests {

    private lateinit var memoryMoviesCache: MemoryMoviesCache

    @Before
    fun before() {
        memoryMoviesCache = MemoryMoviesCache()
    }

    @Test
    fun testWhenSavingPopularMoviesTheyCanAllBeRetrieved() {
        memoryMoviesCache.saveAll(generateMovieEntityList())
        memoryMoviesCache.getAll().test()
                .assertValue { list -> list.size == 5 }
                .assertComplete()
    }

    @Test
    fun testSavedMovieCanBeRetrievedUsingId() {
        memoryMoviesCache.save(getTestMovieEntity(3))
        memoryMoviesCache.get(3).test()
                .assertValue { optional ->
                    optional.hasValue() &&
                            optional.value?.title == "Movie3" && optional.value?.id == 3
                }
                .assertComplete()
    }

    @Test
    fun testAfterClearingTheRepositoryThereAreNoMovies() {
        memoryMoviesCache.saveAll(generateMovieEntityList())
        memoryMoviesCache.getAll().test()
                .assertValue { list -> list.size == 5 }

        memoryMoviesCache.clear()
        memoryMoviesCache.getAll().test()
                .assertValue { list -> list.isEmpty() }
                .assertComplete()
    }

    @Test
    fun testRemovingMovieFromCache() {
        val movieEntity = getTestMovieEntity(3)
        memoryMoviesCache.save(movieEntity)
        memoryMoviesCache.get(3).test()
                .assertValue { optional -> optional.hasValue() }

        memoryMoviesCache.remove(movieEntity)
        memoryMoviesCache.get(3).test()
                .assertValue { optional -> !optional.hasValue() }
    }

    @Test
    fun testIsEmptyReturnsExpectedResult() {
        assertTrue(memoryMoviesCache.isEmpty())
        memoryMoviesCache.saveAll(generateMovieEntityList())
        assertFalse(memoryMoviesCache.isEmpty())
    }
}