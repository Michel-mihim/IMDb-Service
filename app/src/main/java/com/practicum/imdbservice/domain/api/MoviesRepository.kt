package com.practicum.imdbservice.domain.api

import com.practicum.imdbservice.domain.models.Movie
import com.practicum.imdbservice.util.Resourse

interface MoviesRepository {
    fun searchMovies(expression: String): Resourse<List<Movie>>
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}