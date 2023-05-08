package com.example.fakeapichallenge.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.fakeapichallenge.getOrAwaitValue
import com.example.fakeapichallenge.model.MovieItem
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Testing local data source operations
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class MovieDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MovieDatabase
    private lateinit var dao: MovieDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), MovieDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.moviesDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndReadMovieItem() = runTest {
        val item = MovieItem(id = 1, "Stanley Kubrick", "Url", "The shining", 1980)
        dao.insertMovie(item)

        val allItems = dao.readMovies().asLiveData().getOrAwaitValue()

        Truth.assertThat(item).isIn(allItems)
        Truth.assertThat(allItems).hasSize(1)
    }

    @Test
    fun insertAndReadSeveralMoviesItem() = runTest {
        val item = MovieItem(id = 1, "Stanley Kubrick", "Url", "The shining", 1980)
        val item2 = MovieItem(id = 2,"George Lucas", "url", "Star wars", 1977)
        val item3 = MovieItem(id = 3, "The Watchowskis", "url", "The Matrix", 1999)

        dao.insertMovie(item)
        dao.insertMovie(item2)
        dao.insertMovie(item3)

        val allItems = dao.readMovies().asLiveData().getOrAwaitValue()

        Truth.assertThat(item).isIn(allItems)
        Truth.assertThat(item2).isIn(allItems)
        Truth.assertThat(item3).isIn(allItems)
        Truth.assertThat(allItems).hasSize(3)
    }

    @Test
    fun insertSameMovieTwiceShouldReplaceOldOne() = runTest {
        val item1 = MovieItem(id = 1, "Stanley Kubrick", "Url", "The shining", 1980)
        val item2WithUpdatedUrl = MovieItem(id = 1, "Stanley Kubrick", "NewUrl", "The shining", 1980)

        dao.insertMovie(item1)
        dao.insertMovie(item2WithUpdatedUrl)

        val allItems = dao.readMovies().asLiveData().getOrAwaitValue()

        Truth.assertThat(allItems).hasSize(1)
        Truth.assertThat(allItems).contains(item2WithUpdatedUrl)
    }


}