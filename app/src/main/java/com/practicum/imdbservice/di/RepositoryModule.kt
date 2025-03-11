package com.practicum.imdbservice.di

import com.practicum.imdbservice.data.MoviesRepositoryImpl
import com.practicum.imdbservice.domain.api.MoviesRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<MoviesRepository> {
        MoviesRepositoryImpl(get(), get())
    }
}