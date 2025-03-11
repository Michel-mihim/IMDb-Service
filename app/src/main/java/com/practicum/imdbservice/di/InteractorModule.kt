package com.practicum.imdbservice.di

import com.practicum.imdbservice.domain.api.MoviesInteractor
import com.practicum.imdbservice.domain.impl.MoviesInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    single<MoviesInteractor> {
        MoviesInteractorImpl(get())
    }
}