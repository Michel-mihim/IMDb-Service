package com.practicum.imdbservice.domain.api

import com.practicum.imdbservice.domain.models.Movie
import com.practicum.imdbservice.domain.models.MovieCast
import com.practicum.imdbservice.domain.models.MovieDetails
import com.practicum.imdbservice.util.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun searchMovies(expression: String): Flow<Resource<List<Movie>>>
    fun getMovieDetails(movieId: String): Flow<Resource<MovieDetails>>
    fun getMovieCast(movieId: String): Resource<MovieCast>
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}