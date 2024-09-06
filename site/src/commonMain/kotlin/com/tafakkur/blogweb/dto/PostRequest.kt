package com.tafakkur.blogweb.dto

import kotlinx.serialization.Serializable


@Serializable
data class PostRequest(
    val title: String,
    val subtitle: String,
    val content: String,
    val category: String,
    val thumbnailImageUrl: String,
    val tags: MutableList<String>,
    val status: String,
)