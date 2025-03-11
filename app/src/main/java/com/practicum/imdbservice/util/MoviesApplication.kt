package com.practicum.imdbservice.util

import android.app.Application
import com.practicum.imdbservice.di.dataModule
import com.practicum.imdbservice.di.interactorModule
import com.practicum.imdbservice.di.repositoryModule
import com.practicum.imdbservice.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MoviesApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MoviesApplication)
            modules(dataModule, repositoryModule, interactorModule, viewModelModule)
        }
    }

}