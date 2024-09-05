package com.tafakkur.blogweb.data.remote.repository

import com.tafakkur.blogweb.core.storage.LocalStorageManager
import com.tafakkur.blogweb.core.utils.Constants.AUTH_TOKEN
import com.tafakkur.blogweb.core.utils.Constants.EXPIRES_AT
import com.tafakkur.blogweb.data.remote.api.AuthApiService
import com.tafakkur.blogweb.dto.LoginRequest
import com.tafakkur.blogweb.dto.LoginResponse
import com.tafakkur.blogweb.dto.Response
import com.tafakkur.blogweb.repository.AuthRepository
import com.tafakkur.blogweb.repository.LoginStorage

class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val localStorageManager: LocalStorageManager
) : AuthRepository {
    override suspend fun login(loginRequest: LoginRequest): Response<LoginResponse> {
        val response = authApiService.login(loginRequest)

        localStorageManager.setItem(AUTH_TOKEN, response.data.token)
        localStorageManager.setItem(EXPIRES_AT, response.data.expiresIn.toString())
        return response
    }

    override fun getToken(): LoginStorage? {
        val token = localStorageManager.getItem(AUTH_TOKEN)
        val expired = localStorageManager.getItem(EXPIRES_AT)
        if (token != null && expired != null) {
            return LoginStorage(token, expired)
        } else {
            return null
        }
    }

    override fun clearToken() {
        localStorageManager.removeItem(AUTH_TOKEN)
        localStorageManager.removeItem(EXPIRES_AT)
    }

}