package com.example.fakeapichallenge.data.remote

import com.example.fakeapichallenge.data.remote.utils.enqueueMockResponseFromFile
import com.example.fakeapichallenge.data.remote.utils.enqueueMockResponseWithBodyAndResponseCode
import com.example.fakeapichallenge.model.MovieItem
import com.google.common.truth.Truth
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class RemoteMovieRepositoryTest {

    private lateinit var remoteMoviesRepository: RemoteMovieRepository
    private lateinit var apiService: MovieApiService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build().create(MovieApiService::class.java)
        remoteMoviesRepository = RemoteMovieRepository(apiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `simple read from local json`() = runBlocking {
        mockWebServer.enqueueMockResponseFromFile("movies.json")

        val actualResponse = remoteMoviesRepository.fetchRemoteData()
        Truth.assertThat(actualResponse.body()).hasSize(10)
        Truth.assertThat(actualResponse.isSuccessful).isTrue()
        Truth.assertThat(actualResponse.code()).isEqualTo(HttpURLConnection.HTTP_OK)
    }

    @Test
    fun `when no movies, api must return empty with http code 200`() = runBlocking {
        val movies = emptyList<MovieItem>()
        mockWebServer.enqueueMockResponseWithBodyAndResponseCode(movies, HttpURLConnection.HTTP_OK)

        val actualResponse = remoteMoviesRepository.fetchRemoteData()
        Truth.assertThat(actualResponse.body()).hasSize(0)
        Truth.assertThat(actualResponse.isSuccessful).isTrue()
        Truth.assertThat(actualResponse.code()).isEqualTo(HttpURLConnection.HTTP_OK)
    }

    @Test
    fun `when several movies, api must return those movies with http code 200`() = runBlocking {
        val movies = listOf(
            MovieItem(id = 1,"Stanley Kuberic", "url", "The Shining", 1988),
            MovieItem(id = 2,"George Lucas", "url", "Star wars", 1977),
            MovieItem(id = 3, "The Watchowskis", "url", "The Matrix", 1999)
        )
        mockWebServer.enqueueMockResponseWithBodyAndResponseCode(movies, HttpURLConnection.HTTP_OK)

        val actualResponse = remoteMoviesRepository.fetchRemoteData()
        Truth.assertThat(actualResponse.body()).hasSize(3)
        Truth.assertThat(actualResponse.isSuccessful).isTrue()
        Truth.assertThat(actualResponse.code()).isEqualTo(HttpURLConnection.HTTP_OK)
    }

    @Test
    fun `when server error, api must return http code 5xx`() = runBlocking {
        mockWebServer.enqueueMockResponseWithBodyAndResponseCode(emptyList(), HttpURLConnection.HTTP_INTERNAL_ERROR)

        val actualResponse = remoteMoviesRepository.fetchRemoteData()
        Truth.assertThat(actualResponse.isSuccessful).isFalse()
        Truth.assertThat(actualResponse.code()).isEqualTo(HttpURLConnection.HTTP_INTERNAL_ERROR)
    }
}