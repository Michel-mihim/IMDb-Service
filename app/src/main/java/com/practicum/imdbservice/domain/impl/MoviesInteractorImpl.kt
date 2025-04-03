package com.practicum.imdbservice.domain.impl

import com.practicum.imdbservice.domain.api.MoviesInteractor
import com.practicum.imdbservice.domain.api.MoviesRepository
import com.practicum.imdbservice.domain.models.Movie
import com.practicum.imdbservice.domain.models.MovieDetails
import com.practicum.imdbservice.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.Executors

class MoviesInteractorImpl(
    private val repository: MoviesRepository
): MoviesInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchMovies(expression: String): Flow<Pair<List<Movie>?, String?>> {
        return repository.searchMovies(expression).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }
                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }

    override fun getMoviesDetails(movieId: String): Flow<Pair<MovieDetails?, String?>> {
        return repository.getMovieDetails(movieId).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }
                is Resource.Error -> {
                    Pair(result.data, result.message)
                }
            }
        }
    }

    override fun getMovieCast(movieId: String, consumer: MoviesInteractor.MovieCastConsumer) {
        executor.execute {
            // Просто вызываем нужный метод Repository
            when(val resource = repository.getMovieCast(movieId)) {
                is Resource.Success -> { consumer.consume(resource.data, null) }
                is Resource.Error -> { consumer.consume(resource.data, resource.message) }
            }
        }
    }

    override fun addMovieToFavorites(movie: Movie) {
        repository.addMovieToFavorites(movie)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        repository.removeMovieFromFavorites(movie)
    }
}