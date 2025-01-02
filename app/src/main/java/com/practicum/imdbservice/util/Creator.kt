package com.practicum.imdbservice.util

import android.app.Activity
import android.content.Context
import com.practicum.imdbservice.data.MoviesRepositoryImpl
import com.practicum.imdbservice.data.network.RetrofitNetworkClient
import com.practicum.imdbservice.domain.api.MoviesInteractor
import com.practicum.imdbservice.domain.api.MoviesRepository
import com.practicum.imdbservice.domain.impl.MoviesInteractorImpl
import com.practicum.imdbservice.presentation.movies.MoviesSearchPresenter
import com.practicum.imdbservice.presentation.poster.PosterPresenter
import com.practicum.imdbservice.presentation.movies.MoviesView
import com.practicum.imdbservice.presentation.poster.PosterView

object Creator {
    fun provideMoviesInteractor(context: Context): MoviesInteractor{
        return MoviesInteractorImpl(provideMoviesRepository(context))
    }

    private fun provideMoviesRepository(context: Context): MoviesRepository{
        return MoviesRepositoryImpl(RetrofitNetworkClient(context))
    }

    fun provideMoviesSearchPresenter(
        moviesView: MoviesView,
        context: Context,
    ): MoviesSearchPresenter {
        return  MoviesSearchPresenter(
            view = moviesView,
            context = context,
        )
    }

    fun providePosterPresenter(view: PosterView, imageUrl: String): PosterPresenter {
        return PosterPresenter(view, imageUrl)
    }

}