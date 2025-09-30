package com.shiksha.moviesapp.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.shiksha.moviesapp.R
import com.shiksha.moviesapp.adapter.ImagesAdapter
import com.shiksha.moviesapp.databinding.ActivityDetailBinding
import com.shiksha.moviesapp.viewmodel.DetailViewModel
import com.shiksha.moviesapp.viewmodel.DetailViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var imagesAdapter: ImagesAdapter
    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory() // provide factory if needed
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getIntExtra(getString(R.string.movie_id), -1)
        if (movieId != -1) {
            binding.detailloading.visibility = View.VISIBLE
            viewModel.loadMovieById(movieId)
        } else {
            Toast.makeText(this, getString(R.string.invalid_id), Toast.LENGTH_SHORT).show()
            finish()
        }

        imagesAdapter = ImagesAdapter()
        binding.imagesRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.imagesRecyclerView.adapter = imagesAdapter

        viewModel.movie.observe(this) { movie ->
            binding.movieNameTxt.text = movie.title
            binding.movieSummary.text = movie.description
            binding.movieDateTxt.text = movie.releaseDate
            binding.movieRateTxt.text = movie.rating?.toString() ?: "-"
            binding.movieTimeTxt.text = movie.runtime?.let { "$it min" } ?: "-"
            binding.movieActorInfo.text = "Actors: ${movie.actors ?: "Unknown"}"

            Glide.with(this).load(movie.getPosterUrl()).into(binding.posterNormalImg)
            Glide.with(this).load(movie.getPosterUrl()).into(binding.posterBigImg)

            imagesAdapter.submitList(movie.images ?: listOf())
            binding.detailloading.visibility = View.GONE
        }
    }
}
