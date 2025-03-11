package com.practicum.imdbservice.di

import android.content.Context
import com.practicum.imdbservice.data.NetworkClient
import com.practicum.imdbservice.data.localStorage.LocalStorage
import com.practicum.imdbservice.data.network.IMDbApiService
import com.practicum.imdbservice.data.network.RetrofitNetworkClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single<NetworkClient> {
        RetrofitNetworkClient(androidContext(), get())
    }

    single<IMDbApiService> {
        val imdbBaseUrl = "https://tv-api.com"
        Retrofit
            .Builder()
            .baseUrl(imdbBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IMDbApiService::class.java)

    }

    factory<LocalStorage> {
        LocalStorage(get())
    }

    single {
        androidContext().
        getSharedPreferences("local_storage", Context.MODE_PRIVATE)
    }
}