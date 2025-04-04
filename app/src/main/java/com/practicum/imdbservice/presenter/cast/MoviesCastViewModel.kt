package com.practicum.imdbservice.presenter.cast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.imdbservice.domain.api.MoviesInteractor
import com.practicum.imdbservice.domain.models.MovieCast
import com.practicum.imdbservice.ui.cast.models.MoviesCastState
import kotlinx.coroutines.launch

// В конструктор пробросили необходимые для запроса параметры
class MoviesCastViewModel(
    private val movieId: String,
    private val moviesInteractor: MoviesInteractor,
) : ViewModel() {

    // Стандартная обвязка для определения State
    // и наблюдения за ним в UI-слое
    private val stateLiveData = MutableLiveData<MoviesCastState>()
    fun observeState(): LiveData<MoviesCastState> = stateLiveData

    init {
        // При старте экрана покажем ProgressBar
        stateLiveData.postValue(MoviesCastState.Loading)

        // Выполняем сетевой запрос

        viewModelScope.launch {
            moviesInteractor.getMovieCast(movieId).collect { pair ->
                when {
                    pair.first != null -> {
                        stateLiveData.postValue(MoviesCastState.Content(pair.first!!))
                    }

                    else -> {
                        stateLiveData.postValue(MoviesCastState.Error(pair.second ?: "Unknown error"))
                    }
                }
            }
        }
    }
}