package com.example.fakeapichallenge.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fakeapichallenge.Constants
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = Constants.MOVIE_TABLE)
data class MovieItem(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "director")
    @SerializedName("director")
    val director: String,

    @ColumnInfo(name = "posterUrl")
    @SerializedName("posterUrl")
    val posterUrl: String,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String,

    @ColumnInfo(name = "year")
    @SerializedName("year")
    val year: Int
) : Parcelable
