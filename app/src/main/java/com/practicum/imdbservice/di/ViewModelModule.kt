package com.practicum.imdbservice.di

import com.practicum.imdbservice.presenter.movies.MoviesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MoviesViewModel(androidApplication(), get())
    }
}