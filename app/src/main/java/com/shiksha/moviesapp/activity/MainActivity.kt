package com.shiksha.moviesapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.shiksha.moviesapp.R
import com.shiksha.moviesapp.adapter.MovieAdapter
import com.shiksha.moviesapp.databinding.ActivityMainBinding
import com.shiksha.moviesapp.model.Movie
import com.shiksha.moviesapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerViews()
        observeViewModel()
        viewModel.fetchMovies()
    }

    private fun setupRecyclerViews() {
        binding.view1.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.view2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun observeViewModel() {
        viewModel.loading.observe(this) { isLoading ->
            binding.loading1.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.loading2.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.nowPlayingMovies.observe(this) { movies ->
            if (!movies.isNullOrEmpty()) {
                binding.view1.adapter = MovieAdapter(movies) { movie -> openDetail(movie) }
            }
        }

        viewModel.upcomingMovies.observe(this) { movies ->
            if (!movies.isNullOrEmpty()) {
                binding.view2.adapter = MovieAdapter(movies) { movie -> openDetail(movie) }
            }
        }

        viewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun openDetail(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(getString(R.string.movie_id), movie.id)
        }
        startActivity(intent)
    }
}
