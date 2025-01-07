package com.practicum.imdbservice.presentation.movies

import com.practicum.imdbservice.domain.models.Movie
import com.practicum.imdbservice.ui.movies.models.MoviesState

interface MoviesView {

    /*
    fun showPlaceholderMessage(isVisible: Boolean)

    fun showMoviesList(isVisible: Boolean)

    fun showProgressBar(isVisible: Boolean)

    fun changePlaceholderText(newPlaceholderText: String)

    fun updateMoviesList(newMoviesList: List<Movie>)

     */


    fun showLoading()

    fun showContent(movies: List<Movie>)

    fun showError(errorMessage: String)

    fun showEmpty(emptyMessage: String)

    fun render(state: MoviesState)


    fun showToast(message: String)
}