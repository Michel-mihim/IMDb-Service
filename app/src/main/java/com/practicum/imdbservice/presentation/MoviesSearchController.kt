package com.practicum.imdbservice.presentation

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Adapter
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.imdbservice.R
import com.practicum.imdbservice.data.creator.Creator
import com.practicum.imdbservice.domain.api.MoviesInteractor
import com.practicum.imdbservice.domain.models.Movie
import com.practicum.imdbservice.ui.movies.MoviesAdapter

class MoviesSearchController(
    private val activity: Activity,
    private val adapter: MoviesAdapter
) {
    private var moviesInteractor = Creator.provideMoviesInteractor(activity)

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private lateinit var queryInput: EditText
    private lateinit var placeholderMessage: TextView
    private lateinit var moviesList: RecyclerView
    private lateinit var progressBar: ProgressBar

    private val movies = ArrayList<Movie>()

    private val handler = Handler(Looper.getMainLooper())

    private val searchRunnable = Runnable { searchRequest() }

    private fun searchDebounce() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    private fun searchRequest() {
        if (queryInput.text.isNotEmpty()) {
            progressBar.visibility = View.VISIBLE
            moviesList.visibility = View.GONE
            placeholderMessage.visibility = View.GONE

            moviesInteractor.searchMovies(queryInput.text.toString(), object: MoviesInteractor.MoviesConsumer{
                override fun consume(foundMovies: List<Movie>) {
                    handler.post {
                        movies.clear()
                        movies.addAll(foundMovies)
                        adapter.notifyDataSetChanged()
                        if (foundMovies.isEmpty()) {
                            showMessage(activity.getString(R.string.nothing_found), "")
                        } else hideMessage()
                        progressBar.visibility = View.GONE
                        moviesList.visibility = View.VISIBLE
                    }
                }
            } )
        }
    }

    private fun showMessage(text: String, additionalMessage: String) {
        if (text.isNotEmpty()) {
            placeholderMessage.visibility = View.VISIBLE
            movies.clear()
            adapter.notifyDataSetChanged()
            placeholderMessage.text = text
            if (additionalMessage.isNotEmpty()) {
                Toast.makeText(activity, additionalMessage, Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            placeholderMessage.visibility = View.GONE
        }
    }

    private fun hideMessage() {
        placeholderMessage.visibility = View.GONE
    }

    fun onCreate() {
        placeholderMessage = activity.findViewById(R.id.placeholderMessage)
        queryInput = activity.findViewById(R.id.queryInput)
        moviesList = activity.findViewById(R.id.locations)
        progressBar = activity.findViewById(R.id.progressBar)

        adapter.movies = movies

        moviesList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        moviesList.adapter = adapter

        queryInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchDebounce()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    fun onDestroy() {
        handler.removeCallbacks(searchRunnable)
    }
}