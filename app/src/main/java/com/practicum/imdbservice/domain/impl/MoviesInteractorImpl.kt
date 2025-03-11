package com.practicum.imdbservice.domain.impl

import com.practicum.imdbservice.domain.api.MoviesInteractor
import com.practicum.imdbservice.domain.api.MoviesRepository
import com.practicum.imdbservice.domain.models.Movie
import com.practicum.imdbservice.util.Resourse
import java.util.concurrent.Executors

class MoviesInteractorImpl(
    private val repository: MoviesRepository
): MoviesInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchMovies(expression: String, consumer: MoviesInteractor.MoviesConsumer) {
        executor.execute{
            when (val resourse = repository.searchMovies(expression)) {
                is Resourse.Success -> {consumer.consume(resourse.data, null)}
                is Resourse.Error -> {consumer.consume(null, resourse.message)}
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