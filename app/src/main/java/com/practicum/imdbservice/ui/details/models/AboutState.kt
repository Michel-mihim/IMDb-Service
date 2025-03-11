package com.practicum.imdbservice.ui.details.models

import com.practicum.imdbservice.domain.models.MovieDetails

sealed interface AboutState {

    data class Content(
        val movie: MovieDetails
    ) : AboutState

    data class Error(
        val message: String
    ) : AboutState
}