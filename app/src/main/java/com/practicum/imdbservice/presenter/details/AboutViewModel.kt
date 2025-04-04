package com.practicum.imdbservice.presenter.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.imdbservice.domain.api.MoviesInteractor
import com.practicum.imdbservice.domain.models.MovieDetails
import com.practicum.imdbservice.ui.details.models.AboutState
import kotlinx.coroutines.launch

class AboutViewModel(
    private val movieId: String,
    private val moviesInteractor: MoviesInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<AboutState>()
    fun observeState(): LiveData<AboutState> = stateLiveData

    init {

        viewModelScope.launch {
            moviesInteractor.getMoviesDetails(movieId).collect { pair ->
                when {
                    pair.first != null -> {
                        stateLiveData.postValue(AboutState.Content(pair.first!!))
                    }

                    else -> {
                        stateLiveData.postValue(AboutState.Error(pair.second ?: "Unknown error"))
                    }
                }
            }
        }
    }
}