package com.example.fakeapichallenge.data.remote

import retrofit2.Response

interface RemoteRepository<model> {
    suspend fun fetchRemoteData() : Response<model>
}