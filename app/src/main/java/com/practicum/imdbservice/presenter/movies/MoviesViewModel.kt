package com.practicum.imdbservice.presenter.movies

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.imdbservice.R
import com.practicum.imdbservice.domain.api.MoviesInteractor
import com.practicum.imdbservice.domain.models.Movie
import com.practicum.imdbservice.ui.movies.models.MoviesState
import com.practicum.imdbservice.util.SingleLiveEvent


class MoviesViewModel(
    private val application: Application,
    private val moviesInteractor: MoviesInteractor
    ): ViewModel() {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L

    }

    private val handler = Handler(Looper.getMainLooper())

    private var latestSearchText: String? = null

    private val stateLiveData = MutableLiveData<MoviesState>()
    fun observeState(): LiveData<MoviesState> = mediatorStateLiveData

    private val toastState = SingleLiveEvent<String>()
    fun observeToastState(): LiveData<String> = toastState


    private val mediatorStateLiveData = MediatorLiveData<MoviesState>().also { liveData ->
        liveData.addSource(stateLiveData) { movieState ->
            liveData.value = when (movieState) {
                is MoviesState.Content -> MoviesState.Content(movieState.movies.sortedByDescending { it.inFavorite})
                is MoviesState.Empty -> movieState
                is MoviesState.Error -> movieState
                is MoviesState.Loading -> movieState
            }
        }

    }

    override fun onCleared() {
        handler.removeCallbacks(searchRunnable)
    }


    fun toggleFavorite(movie: Movie) {
        if (movie.inFavorite) {
            moviesInteractor.removeMovieFromFavorites(movie)
        } else {
            moviesInteractor.addMovieToFavorites(movie)
        }

        updateMovieContent(movie.id, movie.copy(inFavorite = !movie.inFavorite))
    }


    private fun updateMovieContent(movieId: String, newMovie: Movie) {
        val currentState = stateLiveData.value

        // 2
        if (currentState is MoviesState.Content) {
            // 3
            val movieIndex = currentState.movies.indexOfFirst { it.id == movieId }

            // 4
            if (movieIndex != -1) {
                // 5
                stateLiveData.value = MoviesState.Content(
                    currentState.movies.toMutableList().also {
                        it[movieIndex] = newMovie
                    }
                )
            }
        }
    }



    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        this.latestSearchText = changedText

        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    private val movies = ArrayList<Movie>()

    private val searchRunnable = Runnable {
        val newSearchText = latestSearchText ?: ""
        searchRequest(newSearchText)
    }



    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(
                MoviesState.Loading
            )

            moviesInteractor.searchMovies(
                newSearchText,
                object : MoviesInteractor.MoviesConsumer {
                    override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {
                        if (foundMovies != null) {
                            movies.clear()
                            movies.addAll(foundMovies)
                        }

                        when {
                            errorMessage != null -> {//error
                                renderState(
                                    MoviesState.Error(
                                        errorMessage = application.getString(R.string.something_went_wrong),
                                    )
                                )

                                showToast(errorMessage)
                            }

                            movies.isEmpty() -> {
                                renderState(
                                    MoviesState.Empty(
                                        message = application.getString(R.string.nothing_found),
                                    )
                                )
                            }

                            else -> {
                                renderState(
                                    MoviesState.Content(
                                        movies = movies
                                    )
                                )
                            }
                        }
                    }
                }
            )
        }
    }


    private fun renderState(state: MoviesState) {
        stateLiveData.postValue(state)
    }



    fun showToast(message: String) {
        toastState.postValue(message)
    }
}