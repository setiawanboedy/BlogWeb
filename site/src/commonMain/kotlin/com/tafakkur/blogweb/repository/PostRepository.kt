package com.tafakkur.blogweb.repository

import com.tafakkur.blogweb.core.sealed.ApiResponse
import com.tafakkur.blogweb.dto.*

typealias ApiPostResponse = ApiResponse<Response<PostResponse>>
typealias ApiPostsResponse = ApiResponse<Response<List<PostResponse>>>
interface PostRepository {
    suspend fun createPost(postRequest: PostRequest, thumbnailImage: ByteArray?): ApiPostResponse
    suspend fun updatePost(id: Long, postRequest: PostRequest, thumbnailImage: ByteArray?): ApiPostResponse
    suspend fun getAllPosts(filter: MutableMap<String, Any>): ApiPostsResponse
    suspend fun getPostDetail(id: Long): ApiPostResponse
}