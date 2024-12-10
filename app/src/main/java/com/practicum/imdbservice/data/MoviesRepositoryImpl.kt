package com.practicum.imdbservice.data

import android.util.Log
import com.practicum.imdbservice.data.dto.MoviesSearchRequest
import com.practicum.imdbservice.data.dto.MoviesSearchResponse
import com.practicum.imdbservice.data.dto.Response
import com.practicum.imdbservice.domain.api.MoviesRepository
import com.practicum.imdbservice.domain.models.Movie
import com.practicum.imdbservice.util.Resourse

class MoviesRepositoryImpl(private val networkClient: NetworkClient): MoviesRepository {
    override fun searchMovies(expression: String): Resourse<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))

        return when (response.resultCode) {
            -1 -> {
                Resourse.Error("Проверьте подключение к интернету")
            }

            200 -> {
                Resourse.Success((response as MoviesSearchResponse).results.map {
                    Movie(it.id, it.resultType, it.image, it.title, it.description) })
            }

            else -> {
                Resourse.Error("Ошибка сервера")
            }
        }

    }
}