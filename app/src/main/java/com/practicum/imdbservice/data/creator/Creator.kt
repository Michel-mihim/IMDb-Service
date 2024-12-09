package com.practicum.imdbservice.data.creator

import android.app.Activity
import android.content.Context
import com.practicum.imdbservice.data.MoviesRepositoryImpl
import com.practicum.imdbservice.data.network.RetrofitNetworkClient
import com.practicum.imdbservice.domain.api.MoviesInteractor
import com.practicum.imdbservice.domain.api.MoviesRepository
import com.practicum.imdbservice.domain.impl.MoviesInteractorImpl
import com.practicum.imdbservice.presentation.MoviesSearchController
import com.practicum.imdbservice.ui.movies.MoviesAdapter

object Creator {
    fun provideMoviesInteractor(context: Context): MoviesInteractor{
        return MoviesInteractorImpl(provideMoviesRepository(context))
    }

    private fun provideMoviesRepository(context: Context): MoviesRepository{
        return MoviesRepositoryImpl(RetrofitNetworkClient(context))
    }

    fun provideMoviesSearchController(activity: Activity, adapter: MoviesAdapter): MoviesSearchController {
        return  MoviesSearchController(activity, adapter)
    }

}