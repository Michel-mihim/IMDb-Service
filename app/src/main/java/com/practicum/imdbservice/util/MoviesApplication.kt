package com.practicum.imdbservice.util

import android.app.Application
import com.practicum.imdbservice.presentation.movies.MoviesSearchPresenter

class MoviesApplication: Application() {
    var moviesSearchPresenter: MoviesSearchPresenter? = null
}