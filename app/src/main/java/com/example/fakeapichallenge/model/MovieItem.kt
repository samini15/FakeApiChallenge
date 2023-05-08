package com.example.fakeapichallenge.model

import com.google.gson.annotations.SerializedName

data class MovieItem(
    @SerializedName("director")
    val director: String,

    @SerializedName("posterUrl")
    val posterUrl: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("year")
    val year: Int
)
