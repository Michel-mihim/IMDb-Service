package com.practicum.imdbservice.data

import com.practicum.imdbservice.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}