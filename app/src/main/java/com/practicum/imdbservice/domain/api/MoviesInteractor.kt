package com.practicum.imdbservice.domain.api

import com.practicum.imdbservice.domain.models.Movie
import com.practicum.imdbservice.domain.models.MovieCast
import com.practicum.imdbservice.domain.models.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MoviesInteractor {
    fun searchMovies(expression: String): Flow<Pair<List<Movie>?, String?>>

    fun getMoviesDetails(movieId: String): Flow<Pair<MovieDetails?, String?>>


    fun getMovieCast(movieId: String, consumer: MovieCastConsumer)
    interface MovieCastConsumer {
        fun consume(movieCast: MovieCast?, errorMessage: String?)
    }
    /*
    interface MoviesConsumer{
        fun consume(foundMovies: List<Movie>?, errorMessage: String?)
    }

     */

    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}