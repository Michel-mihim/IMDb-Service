package com.practicum.imdbservice.ui.movies.models

import com.practicum.imdbservice.domain.models.Movie

data class MoviesState(
    val movies: List<Movie>,
    val isLoading: Boolean,
    val errorMessage: String?
)
