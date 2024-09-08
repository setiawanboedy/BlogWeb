package com.tafakkur.blogweb.repository

import com.tafakkur.blogweb.dto.*

data class LoginStorage(
    val token: String,
    val expired: String
)
interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse?>
    fun getToken(): LoginStorage?
    fun clearToken()

}