package com.practicum.imdbservice.ui.cast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.practicum.imdbservice.R
import com.practicum.imdbservice.databinding.ActivityMoviesCastBinding

class MoviesCastActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoviesCastBinding

    companion object {

        private const val ARGS_MOVIE_ID = "movie_id"

        fun newInstance(context: Context, movieId: String): Intent {
            return Intent(context, MoviesCastActivity::class.java).apply {
                putExtra(ARGS_MOVIE_ID, movieId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoviesCastBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}