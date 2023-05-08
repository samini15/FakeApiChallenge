package com.example.fakeapichallenge.utils

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T): NetworkResult<T>(data)
    class Progress<T>: NetworkResult<T>()
    class Error<T>(message: String, data: T? = null): NetworkResult<T>(data, message)
}
