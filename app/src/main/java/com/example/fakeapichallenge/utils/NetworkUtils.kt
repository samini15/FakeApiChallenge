package com.example.fakeapichallenge.utils

import retrofit2.Response
import java.net.HttpURLConnection

object NetworkUtils {

    fun <T> handleApiResponse(response: Response<T>): NetworkResult<T> {
        return when {
            (response.code() == HttpURLConnection.HTTP_INTERNAL_ERROR) ->
                NetworkResult.Error("Server Internal error")

            response.isSuccessful -> {
                val responseBody = response.body()
                NetworkResult.Success(responseBody!!)
            }

            else -> NetworkResult.Error(response.message())
        }
    }
}