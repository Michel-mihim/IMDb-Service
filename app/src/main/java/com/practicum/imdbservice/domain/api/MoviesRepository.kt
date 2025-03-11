package com.practicum.imdbservice.domain.api

import com.practicum.imdbservice.domain.models.Movie
import com.practicum.imdbservice.domain.models.MovieDetails
import com.practicum.imdbservice.util.Resource

interface MoviesRepository {
    fun searchMovies(expression: String): Resource<List<Movie>>
    fun getMovieDetails(movieId: String): Resource<MovieDetails>
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}