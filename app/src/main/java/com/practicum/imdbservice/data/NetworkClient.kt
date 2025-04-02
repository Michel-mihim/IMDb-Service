package com.practicum.imdbservice.data

import com.practicum.imdbservice.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
    //suspend fun doRequestSuspend(dto: Any): Response
}