package com.shiksha.moviesapp.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("overview")
    val description: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val runtime: Int? = null,
    val vote_average: Float? = null,
    val credits: Credits? = null,
    var images: List<String>? = null
) {
    val rating: Float?
        get() = vote_average

    val actors: String?
        get() = credits?.cast?.take(3)?.joinToString(", ") { it.name }

    fun getPosterUrl(): String = "https://image.tmdb.org/t/p/w500$posterPath"
}

data class Credits(
    val cast: List<Actor>
)

data class Actor(
    val name: String
)
