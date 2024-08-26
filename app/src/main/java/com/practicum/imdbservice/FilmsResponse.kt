package com.practicum.imdbservice

class FilmsResponse(val searchType: String,
                    val expression: String,
                    val results: ArrayList<Film>,
                    val errorMessage: String)