package com.practicum.imdbservice.data.dto

import com.practicum.imdbservice.domain.models.Movie

data class MoviesSearchResponse(val searchType: String,
                                val expression: String,
                                val results: List<MovieDto>): Response()