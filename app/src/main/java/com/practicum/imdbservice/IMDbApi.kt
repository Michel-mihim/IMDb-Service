package com.practicum.imdbservice

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IMDbApi {

    @GET("/en/API/SearchMovie/k_zcuw1ytf/{expression}")
    fun getFilms(
        @Path("expression") expression: String
    ): Call<FilmsResponse>

}