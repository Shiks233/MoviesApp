package com.shiksha.moviesapp.viewmodel

import androidx.lifecycle.*
import com.shiksha.moviesapp.model.Movie
import com.shiksha.moviesapp.repository.MovieRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> get() = _movie

    fun loadMovieById(id: Int) {
        viewModelScope.launch {
            try {
                val movieDetails = repository.getMovieDetails(id)
                _movie.value = movieDetails
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
