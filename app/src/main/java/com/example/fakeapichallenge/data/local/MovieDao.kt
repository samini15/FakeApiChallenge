package com.example.fakeapichallenge.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fakeapichallenge.model.MovieItem
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_table m ORDER BY m.id ASC")
    fun readMovies(): Flow<List<MovieItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(recipesEntity: MovieItem)

}