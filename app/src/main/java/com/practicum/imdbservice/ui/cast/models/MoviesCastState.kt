package com.practicum.imdbservice.ui.cast.models

import com.practicum.imdbservice.domain.models.MovieCast

sealed interface MoviesCastState {

    object Loading : MoviesCastState

    data class Content(
        val movie: MovieCast,
    ) : MoviesCastState

    data class Error(
        val message: String,
    ) : MoviesCastState

}