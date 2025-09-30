package com.shiksha.moviesapp.repository


import com.shiksha.moviesapp.model.MovieResponse
import com.shiksha.moviesapp.helper.TMDBApi
import com.shiksha.moviesapp.model.Movie

class MovieRepository(private val api: TMDBApi) {
    private val apiKey = "4919fce6269745b823efa57d64b4774d"

    suspend fun getNowPlayingMovies(): MovieResponse {
        return api.getNowPlayingMovies(apiKey)
    }

    suspend fun getUpcomingMovies(): MovieResponse {
        return api.getUpcomingMovies(apiKey)
    }
        suspend fun getMovieDetails(id: Int): Movie {
        return api.getMovieDetails(id, apiKey)
    }



}
