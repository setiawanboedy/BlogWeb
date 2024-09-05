package com.tafakkur.blogweb.dto

import kotlinx.serialization.Serializable

@Serializable
data class Response<T>(
    val status: String,
    val message: String,
    val data: T
)
