package com.tafakkur.blogweb.dto

import kotlinx.serialization.Serializable


@Serializable
data class LoginResponse(
    val token: String,
    val expiresIn: String
)
