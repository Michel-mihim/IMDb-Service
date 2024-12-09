package com.practicum.imdbservice

import com.practicum.imdbservice.domain.models.Movie

data class MoviesSearchResponse(val searchType: String,
                                val expression: String,
                                val results: List<Movie>)