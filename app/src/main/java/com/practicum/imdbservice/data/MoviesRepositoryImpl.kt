package com.practicum.imdbservice.data

import android.util.Log
import com.practicum.imdbservice.data.dto.MoviesSearchRequest
import com.practicum.imdbservice.data.dto.MoviesSearchResponse
import com.practicum.imdbservice.domain.api.MoviesRepository
import com.practicum.imdbservice.domain.models.Movie

class MoviesRepositoryImpl(private val networkClient: NetworkClient): MoviesRepository {
    override fun searchMovies(expression: String): List<Movie> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        if (response.resultCode == 200) {
            return (response as MoviesSearchResponse).results.map {
                Movie(it.id, it.resultType, it.image, it.title, it.description) }
        } else {
            return emptyList()
        }
    }
}