package com.example.fakeapichallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fakeapichallenge.model.MovieItem

@Database(
    entities = [MovieItem::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun moviesDao() : MovieDao
}