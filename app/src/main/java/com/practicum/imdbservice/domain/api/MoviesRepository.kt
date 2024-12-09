package com.practicum.imdbservice.domain.api

import com.practicum.imdbservice.domain.models.Movie

interface MoviesRepository {
    fun searchMovies(expression: String): List<Movie>
}