package com.practicum.imdbservice

class FilmsResponse(val searchType: String,
                    val expression: String,
                    val results: List<Film>,
                    val errorMessage: String)