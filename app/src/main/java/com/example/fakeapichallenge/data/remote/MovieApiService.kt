package com.example.fakeapichallenge.data.remote

import com.example.fakeapichallenge.model.Movies
import retrofit2.Response
import retrofit2.http.GET

interface MovieApiService {

    @GET("/da/FakeAPI/movies.json")
    suspend fun fetchMovies() : Response<Movies>

}