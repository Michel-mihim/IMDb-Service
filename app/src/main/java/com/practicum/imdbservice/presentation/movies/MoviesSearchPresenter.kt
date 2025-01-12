package com.practicum.imdbservice.presentation.movies

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.imdbservice.R
import com.practicum.imdbservice.util.Creator
import com.practicum.imdbservice.domain.api.MoviesInteractor
import com.practicum.imdbservice.domain.models.Movie
import com.practicum.imdbservice.ui.movies.MoviesAdapter
import com.practicum.imdbservice.ui.movies.models.MoviesState
import moxy.MvpPresenter

class MoviesSearchPresenter(
    private val context: Context,

): MvpPresenter<MoviesView>() {

    private var lastSearchText: String? = null

    private val moviesInteractor = Creator.provideMoviesInteractor(context)
    private val handler = Handler(Looper.getMainLooper())

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private val movies = ArrayList<Movie>()

    private val searchRunnable = Runnable {
        val newSearchText = lastSearchText ?: ""
        searchRequest(newSearchText)
    }

    fun searchDebounce(changedText: String) {
        if (lastSearchText == changedText) {
            return
        }

        this.lastSearchText = changedText

        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(
                MoviesState(
                    movies = movies,
                    isLoading = true,
                    errorMessage = null,
                )
            )

            moviesInteractor.searchMovies(
                newSearchText,
                object : MoviesInteractor.MoviesConsumer {
                    override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {
                        handler.post {
                            if (foundMovies != null) {
                                movies.clear()
                                movies.addAll(foundMovies)
                            }

                            when {
                                errorMessage != null -> {
                                    renderState(
                                        MoviesState(
                                            movies = emptyList(),
                                            isLoading = false,
                                            errorMessage = context.getString(R.string.something_went_wrong),
                                        )
                                    )

                                    viewState.showToast(errorMessage)
                                }

                                movies.isEmpty() -> {
                                    renderState(
                                        MoviesState(
                                            movies = emptyList(),
                                            isLoading = false,
                                            errorMessage = context.getString(R.string.nothing_found),
                                        )
                                    )
                                }

                                else -> {
                                    renderState(
                                        MoviesState(
                                            movies = movies,
                                            isLoading = false,
                                            errorMessage = null
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            )
        }
    }

    private fun renderState(state: MoviesState) {
        viewState.render(state)
    }

    override fun onDestroy() {
        handler.removeCallbacks(searchRunnable)
    }
}