package com.practicum.imdbservice

class FilmsResponse(val searchType: String,
                    val expression: String,
                    val films: ArrayList<Film>,
                    val errorMessage: String)