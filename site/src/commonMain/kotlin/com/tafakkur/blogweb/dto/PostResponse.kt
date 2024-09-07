package com.tafakkur.blogweb.dto

import kotlinx.serialization.Serializable


@Serializable
data class PostResponse(
    val id: Long,
    val title: String,
    val subtitle: String,
    val author: String,
    val category: String,
    val thumbnailImageUrl: String,
    val slug: String,
    val tags: MutableList<String>,
    val status: String,
    val popular: Boolean = false,
    val main: Boolean = false,
    val sponsored: Boolean = false,
    val createdAt: String,
)
