package com.practicum.imdbservice.ui.movies.models

import com.practicum.imdbservice.domain.models.Movie

sealed interface MoviesState {

    object Loading: MoviesState

    data class Content(val movies: List<Movie>): MoviesState

    data class Error(val errorMessage: String): MoviesState

    data class Empty(val message: String): MoviesState
}