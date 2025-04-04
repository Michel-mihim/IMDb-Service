package com.practicum.imdbservice.data

import com.practicum.imdbservice.data.converters.MovieCastConverter
import com.practicum.imdbservice.data.dto.MovieCastRequest
import com.practicum.imdbservice.data.dto.MovieCastResponse
import com.practicum.imdbservice.data.dto.MovieDetailsRequest
import com.practicum.imdbservice.data.dto.MovieDetailsResponse
import com.practicum.imdbservice.data.dto.MoviesSearchRequest
import com.practicum.imdbservice.data.dto.MoviesSearchResponse
import com.practicum.imdbservice.data.localStorage.LocalStorage
import com.practicum.imdbservice.domain.api.MoviesRepository
import com.practicum.imdbservice.domain.models.Movie
import com.practicum.imdbservice.domain.models.MovieCast
import com.practicum.imdbservice.domain.models.MovieCastPerson
import com.practicum.imdbservice.domain.models.MovieDetails
import com.practicum.imdbservice.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val localStorage: LocalStorage,
    private val movieCastConverter: MovieCastConverter
): MoviesRepository {
    override fun searchMovies(expression: String): Flow<Resource<List<Movie>>> = flow {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))

        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к интернету"))
            }

            200 -> {
                val stored = localStorage.getSavedFavorites()

                emit(Resource.Success((response as MoviesSearchResponse).results.map {
                    Movie(
                        id = it.id,
                        resultType = it.resultType,
                        image = it.image,
                        title = it.title,
                        description = it.description,
                        inFavorite = stored.contains(it.id)) }))
            }

            else -> {
                emit(Resource.Error("Ошибка сервера"))
            }
        }

    }

    override fun getMovieDetails(movieId: String): Flow<Resource<MovieDetails>> = flow {
        val response = networkClient.doRequest(MovieDetailsRequest(movieId))

        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к интернету"))
            }
            200 -> {
                with(response as MovieDetailsResponse) {
                    emit(Resource.Success(
                        MovieDetails(
                            id = id,
                            title = title,
                            imDbRating = imDbRating,
                            year = year,
                            countries = countries,
                            genres = genres,
                            directors = directors,
                            writers = writers,
                            stars = stars,
                            plot = plot,
                        )
                    ))
                }
            }
            else -> {
                emit(Resource.Error("Ошибка сервера"))
            }
        }
    }

    override fun getMovieCast(movieId: String): Flow<Resource<MovieCast>> = flow {
        val response = networkClient.doRequest(MovieCastRequest(movieId))
        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к интернету"))
            }
            200 -> {
                // Осталось написать конвертацию!
                with(response as MovieCastResponse) {
                    emit(Resource.Success(
                        data = movieCastConverter.convert(response as MovieCastResponse)
                    ))
                }
            }
            else -> {
                emit(Resource.Error("Ошибка сервера"))
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