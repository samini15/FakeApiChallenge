package com.example.fakeapichallenge.injection

import android.content.Context
import androidx.room.Room
import com.example.fakeapichallenge.Constants
import com.example.fakeapichallenge.data.local.LocalMovieRepository
import com.example.fakeapichallenge.data.local.LocalRepository
import com.example.fakeapichallenge.data.local.MovieDao
import com.example.fakeapichallenge.data.local.MovieDatabase
import com.example.fakeapichallenge.model.MovieItem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideMoviesDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        MovieDatabase::class.java,
        Constants.MOVIE_DB
    ).build()


    @Singleton
    @Provides
    fun provideMoviesDao(moviesDatabase: MovieDatabase) = moviesDatabase.moviesDao()

    @Singleton
    @Provides
    fun provideMovieLocalRepository(moviesDao: MovieDao): LocalRepository<MovieItem> = LocalMovieRepository(moviesDao)
}