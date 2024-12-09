package com.practicum.imdbservice.data.creator

import android.app.Activity
import com.practicum.imdbservice.data.MoviesRepositoryImpl
import com.practicum.imdbservice.data.network.RetrofitNetworkClient
import com.practicum.imdbservice.domain.api.MoviesInteractor
import com.practicum.imdbservice.domain.api.MoviesRepository
import com.practicum.imdbservice.domain.impl.MoviesInteractorImpl
import com.practicum.imdbservice.presentation.MoviesSearchController
import com.practicum.imdbservice.ui.movies.MoviesAdapter

object Creator {
    fun provideMoviesInteractor(): MoviesInteractor{
        return MoviesInteractorImpl(provideMoviesRepository())
    }

    private fun provideMoviesRepository(): MoviesRepository{
        return MoviesRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideMoviesSearchController(activity: Activity, adapter: MoviesAdapter): MoviesSearchController {
        return  MoviesSearchController(activity, adapter)
    }

}