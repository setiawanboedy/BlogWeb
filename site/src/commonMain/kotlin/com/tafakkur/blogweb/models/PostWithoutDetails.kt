package com.tafakkur.blogweb.models

data class PostWithoutDetails(
    val id: Long = 0,
    val author: String,
    val title: String,
    val subtitle: String,
    val thumbnail: String,
    val status: String,
    val category: Category,
    val popular: Boolean = false,
    val main: Boolean = false,
    val sponsored: Boolean = false,
    val createdAt: String,
)
