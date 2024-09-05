package com.tafakkur.blogweb.repository

import com.tafakkur.blogweb.dto.LoginRequest
import com.tafakkur.blogweb.dto.LoginResponse
import com.tafakkur.blogweb.dto.Response

data class LoginStorage(
    val token: String,
    val expired: String
)

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse?>
    fun getToken(): LoginStorage?
    fun clearToken()
}