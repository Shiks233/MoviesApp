package com.shiksha.moviesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shiksha.moviesapp.model.Movie
import com.shiksha.moviesapp.repository.MovieRepository
import com.shiksha.moviesapp.helper.RetrofitClient
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val api = RetrofitClient.instance

    private val repository = MovieRepository(api)

    private val _nowPlayingMovies = MutableLiveData<List<Movie>>()
    val nowPlayingMovies: LiveData<List<Movie>> = _nowPlayingMovies

    private val _upcomingMovies = MutableLiveData<List<Movie>>()
    val upcomingMovies: LiveData<List<Movie>> = _upcomingMovies

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchMovies() {
        _loading.value = true
        _error.value = null
        viewModelScope.launch {
            try {
                val nowPlayingResponse = repository.getNowPlayingMovies()
                val upcomingResponse = repository.getUpcomingMovies()

                _nowPlayingMovies.value = nowPlayingResponse.results
                _upcomingMovies.value = upcomingResponse.results

            } catch (e: Exception) {
                _error.value = "Failed to load movies: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}
