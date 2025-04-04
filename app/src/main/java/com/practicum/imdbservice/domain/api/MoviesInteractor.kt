package com.practicum.imdbservice.domain.api

import com.practicum.imdbservice.domain.models.Movie
import com.practicum.imdbservice.domain.models.MovieCast
import com.practicum.imdbservice.domain.models.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MoviesInteractor {
    fun searchMovies(expression: String): Flow<Pair<List<Movie>?, String?>>

    fun getMoviesDetails(movieId: String): Flow<Pair<MovieDetails?, String?>>

    fun getMovieCast(movieId: String): Flow<Pair<MovieCast?, String?>>

    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}