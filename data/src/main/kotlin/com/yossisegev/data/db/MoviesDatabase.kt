package com.yossisegev.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.yossisegev.data.entities.MovieData

/**
 * Created by Yossi Segev on 20/01/2018.
 */
@Database(entities = [MovieData::class], version = 1)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun getMoviesDao(): MoviesDao
}