package com.tafakkur.blogweb.dto

import kotlinx.serialization.Serializable


@Serializable
data class PostRequest(
    val title: String,
    val subtitle: String,
    val content: String,
    val category: String,
    val thumbnailName: String,
    val thumbnailLinkUrl: String,
    val main: Boolean,
    val popular: Boolean,
    val sponsored: Boolean,
    val tags: List<String>,
    val status: String,
)