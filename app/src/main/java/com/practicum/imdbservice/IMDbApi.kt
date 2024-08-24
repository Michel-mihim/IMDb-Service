package com.practicum.imdbservice

import retrofit2.Call
import retrofit2.http.GET

interface IMDbApi {

    @GET("/")
    fun getFilms(

    ): Call<>

}