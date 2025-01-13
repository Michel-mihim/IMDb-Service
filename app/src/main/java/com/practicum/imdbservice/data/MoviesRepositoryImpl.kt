package com.practicum.imdbservice.data

import android.util.Log
import com.practicum.imdbservice.data.dto.MoviesSearchRequest
import com.practicum.imdbservice.data.dto.MoviesSearchResponse
import com.practicum.imdbservice.data.dto.Response
import com.practicum.imdbservice.data.localStorage.LocalStorage
import com.practicum.imdbservice.domain.api.MoviesRepository
import com.practicum.imdbservice.domain.models.Movie
import com.practicum.imdbservice.util.Resourse

class MoviesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val localStorage: LocalStorage
): MoviesRepository {
    override fun searchMovies(expression: String): Resourse<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))

        return when (response.resultCode) {
            -1 -> {
                Resourse.Error("Проверьте подключение к интернету")
            }

            200 -> {
                val stored = localStorage.getSavedFavorites()

                Resourse.Success((response as MoviesSearchResponse).results.map {
                    Movie(
                        id = it.id,
                        resultType = it.resultType,
                        image = it.image,
                        title = it.title,
                        description = it.description,
                        inFavorite = stored.contains(it.id)) })
            }

            else -> {
                Resourse.Error("Ошибка сервера")
            }
        }

    }

    override fun addMovieToFavorites(movie: Movie) {
        localStorage.addToFavorites(movie.id)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        localStorage.removeFromFavorites(movie.id)
    }
}