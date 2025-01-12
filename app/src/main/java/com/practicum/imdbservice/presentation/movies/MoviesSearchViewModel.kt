package com.practicum.imdbservice.presentation.movies

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practicum.imdbservice.R
import com.practicum.imdbservice.util.Creator
import com.practicum.imdbservice.domain.api.MoviesInteractor
import com.practicum.imdbservice.domain.models.Movie
import com.practicum.imdbservice.ui.movies.models.MoviesState
import com.practicum.imdbservice.util.SingleLiveEvent
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class MoviesSearchViewModel(application: Application): AndroidViewModel(application) {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L

        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MoviesSearchViewModel(this[APPLICATION_KEY] as Application)
            }
        }
    }

    private val moviesInteractor = Creator.provideMoviesInteractor(getApplication<Application>())
    private val handler = Handler(Looper.getMainLooper())

    private var latestSearchText: String? = null

    private val stateLiveData = MutableLiveData<MoviesState>()
    fun observeState(): LiveData<MoviesState> = stateLiveData

    private val toastState = SingleLiveEvent<String>()
    fun observeToastState(): LiveData<String> = toastState

    override fun onCleared() {
        handler.removeCallbacks(searchRunnable)
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
                        if (foundMovies != null) {
                            movies.clear()
                            movies.addAll(foundMovies)
                        }

                        when {
                            errorMessage != null -> {//error
                                renderState(
                                    MoviesState(
                                        movies = emptyList(),
                                        isLoading = false,
                                        errorMessage = getApplication<Application>().getString(R.string.something_went_wrong),
                                    )
                                )

                                showToast(errorMessage)
                            }

                            movies.isEmpty() -> {
                                renderState(
                                    MoviesState(
                                        movies = emptyList(),
                                        isLoading = false,
                                        errorMessage = getApplication<Application>().getString(R.string.nothing_found),
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