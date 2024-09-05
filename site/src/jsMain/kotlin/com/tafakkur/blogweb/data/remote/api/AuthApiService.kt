package com.tafakkur.blogweb.data.remote.api

import com.tafakkur.blogweb.config.Config
import com.tafakkur.blogweb.dto.LoginRequest
import com.tafakkur.blogweb.dto.LoginResponse
import com.tafakkur.blogweb.dto.Response
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class AuthApiService(private val client: HttpClient) {
    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse> {
        return client.post("${Config.BASE_URL}api/auth/login"){
            contentType(ContentType.Application.Json)
            setBody(loginRequest)
        }.body()
    }
}