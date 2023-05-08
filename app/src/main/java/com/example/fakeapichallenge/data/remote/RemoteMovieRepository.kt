package com.example.fakeapichallenge.data.remote

import com.example.fakeapichallenge.model.Movies
import retrofit2.Response

class RemoteMovieRepository constructor(private val movieApiService: MovieApiService)
    : RemoteRepository<Movies> {
    override suspend fun fetchRemoteData(): Response<Movies> {
        TODO("Not yet implemented")
    }
}