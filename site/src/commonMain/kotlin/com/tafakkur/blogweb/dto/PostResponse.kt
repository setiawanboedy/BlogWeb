package com.tafakkur.blogweb.dto

import kotlinx.serialization.Serializable


@Serializable
data class PostResponse(
    val title: String,
    val subtitle: String,
    val author: String,
    val category: String,
    val thumbnailImageUrl: String,
    val slug: String,
    val tags: MutableList<String>,
    val status: String,
)
