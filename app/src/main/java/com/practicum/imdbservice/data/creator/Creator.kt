package com.practicum.imdbservice.data.creator

import com.practicum.imdbservice.data.MoviesRepositoryImpl
import com.practicum.imdbservice.data.network.RetrofitNetworkClient
import com.practicum.imdbservice.domain.api.MoviesInteractor
import com.practicum.imdbservice.domain.api.MoviesRepository
import com.practicum.imdbservice.domain.impl.MoviesInteractorImpl

object Creator {
    fun provideMoviesInteractor(): MoviesInteractor{
        return MoviesInteractorImpl(provideMoviesRepository())
    }

    private fun provideMoviesRepository(): MoviesRepository{
        return MoviesRepositoryImpl(RetrofitNetworkClient())
    }


}