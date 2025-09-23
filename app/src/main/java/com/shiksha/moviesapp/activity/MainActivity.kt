package com.shiksha.moviesapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
//import com.shiksha.moviesapp.R
import com.shiksha.moviesapp.adapter.MovieAdapter
import com.shiksha.moviesapp.databinding.ActivityMainBinding
import com.shiksha.moviesapp.domain.Movie
import com.shiksha.moviesapp.helper.RetrofitClient
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val apiKey = "4919fce6269745b823efa57d64b4774d"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerViews()
        fetchMovies()
    }
    private fun setupRecyclerViews() {
        binding.view1.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.view2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun fetchMovies() {
        lifecycleScope.launch {
            try {
                binding.loading1.visibility = android.view.View.VISIBLE
                binding.loading2.visibility = android.view.View.VISIBLE

                val nowPlayingResponse = RetrofitClient.instance.getNowPlayingMovies(apiKey)
                val upcomingResponse = RetrofitClient.instance.getUpcomingMovies(apiKey)

                binding.view1.adapter = MovieAdapter(nowPlayingResponse.results) { movie ->
                    openDetail(movie)
                }
                binding.view2.adapter = MovieAdapter(upcomingResponse.results) { movie ->
                    openDetail(movie)
                }

                binding.loading1.visibility = android.view.View.GONE
                binding.loading2.visibility = android.view.View.GONE

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun openDetail(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("movie_id", movie.id)
        intent.putExtra("movie_title", movie.title)
        intent.putExtra("movie_description", movie.description)
        intent.putExtra("movie_release_date", movie.releaseDate)
        intent.putExtra("movie_poster_url", movie.getPosterUrl())
        startActivity(intent)
    }
}
