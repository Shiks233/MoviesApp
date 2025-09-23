package com.shiksha.moviesapp.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.shiksha.moviesapp.adapter.ImagesAdapter
import com.shiksha.moviesapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var imagesAdapter: ImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieTitle = intent.getStringExtra("movie_title") ?: "N/A"
        val movieSummary = intent.getStringExtra("movie_description") ?: "No Description Available"
        val movieReleaseDate = intent.getStringExtra("movie_release_date") ?: "Unknown"
        val movieRating = intent.getFloatExtra("movie_rating", 0f)
        val movieRuntime = intent.getIntExtra("movie_runtime", 0)
        val movieActors = intent.getStringExtra("movie_actors") ?: "Unknown"
        val posterUrl = intent.getStringExtra("movie_poster_url") ?: ""
        val posterBackgroundUrl = intent.getStringExtra("movie_poster_background_url") ?: ""
        val imagesList = intent.getStringArrayListExtra("movie_images") ?: arrayListOf()

        binding.movieNameTxt.text = movieTitle
        binding.movieSummary.text = movieSummary
        binding.movieDateTxt.text = movieReleaseDate
        binding.movieRateTxt.text = if (movieRating > 0) movieRating.toString() else "-"
        binding.movieTimeTxt.text = if (movieRuntime > 0) "$movieRuntime min" else "-"
        binding.movieActorInfo.text = "Actors: $movieActors"

        Glide.with(this)
            .load(posterUrl)
            .into(binding.posterNormalImg)

        Glide.with(this)
            .load(posterBackgroundUrl)
            .into(binding.posterBigImg)

        imagesAdapter = ImagesAdapter()
        binding.imagesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = imagesAdapter
        }

        imagesAdapter.submitList(imagesList)
        binding.detailloading.visibility = View.GONE
    }
}
