package com.yossisegev.movienight.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.yossisegev.data.api.Api
import com.yossisegev.data.db.MoviesDatabase
import com.yossisegev.data.db.RoomFavoritesMovieCache
import com.yossisegev.data.mappers.MovieDataEntityMapper
import com.yossisegev.data.mappers.MovieEntityDataMapper
import com.yossisegev.data.repositories.*
import com.yossisegev.domain.MoviesCache
import com.yossisegev.domain.MoviesRepository
import com.yossisegev.movienight.di.DI
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Yossi Segev on 13/11/2017.
 */
@Module
@Singleton
class DataModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context): MoviesDatabase {
        return Room.databaseBuilder(
            context,
            MoviesDatabase::class.java,
            "movies_db").build()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: Api,
                               @Named(DI.inMemoryCache) cache: MoviesCache): MoviesRepository {

        val cachedMoviesDataStore = CachedMoviesDataStore(cache)
        val remoteMoviesDataStore = RemoteMoviesDataStore(api)
        return MoviesRepositoryImpl(cachedMoviesDataStore, remoteMoviesDataStore)
    }

    @Singleton
    @Provides
    @Named(DI.inMemoryCache)
    fun provideInMemoryMoviesCache(): MoviesCache {
        return MemoryMoviesCache()
    }

    @Singleton
    @Provides
    @Named(DI.favoritesCache)
    fun provideFavoriteMoviesCache(moviesDatabase: MoviesDatabase,
                                   entityDataMapper: MovieEntityDataMapper,
                                   dataEntityMapper: MovieDataEntityMapper): MoviesCache {
        return RoomFavoritesMovieCache(moviesDatabase, entityDataMapper, dataEntityMapper)
    }
}