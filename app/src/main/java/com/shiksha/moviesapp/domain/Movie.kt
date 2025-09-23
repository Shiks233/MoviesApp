package com.shiksha.moviesapp.domain

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("overview")
    val description: String,
    @SerializedName("release_date")
    val releaseDate: String
) {
    fun getPosterUrl(): String = "https://image.tmdb.org/t/p/w500$posterPath"
}
