package com.practicum.imdbservice.domain.api

import com.practicum.imdbservice.domain.models.Movie

interface MoviesInteractor {
    fun searchMovies(expression: String, consumer: MoviesConsumer)

    interface MoviesConsumer{
        fun consume(foundMovies: List<Movie>)
    }
}