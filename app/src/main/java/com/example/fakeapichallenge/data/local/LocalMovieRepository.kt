package com.example.fakeapichallenge.data.local

import com.example.fakeapichallenge.model.MovieItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalMovieRepository @Inject constructor(private val movieDao: MovieDao) : LocalRepository<MovieItem> {

    override fun readEntities(): Flow<List<MovieItem>> {
        return movieDao.readMovies()
    }

    override suspend fun insertEntity(entity: MovieItem) = movieDao.insertMovie(entity)
}