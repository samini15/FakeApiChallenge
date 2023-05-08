package com.example.fakeapichallenge.data.remote.utils

import com.example.fakeapichallenge.model.MovieItem
import com.google.gson.Gson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source

fun MockWebServer.enqueueMockResponseFromFile(fileName: String) {
    javaClass.classLoader?.let {
        val inputStream = it.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        this.enqueue(mockResponse)
    }
}

fun MockWebServer.enqueueMockResponseWithBodyAndResponseCode(body: List<MovieItem>, responseCode: Int) {
    val expectedResponse = MockResponse()
        .setResponseCode(responseCode)
        .setBody(Gson().toJson(body))

    this.enqueue(expectedResponse)
}