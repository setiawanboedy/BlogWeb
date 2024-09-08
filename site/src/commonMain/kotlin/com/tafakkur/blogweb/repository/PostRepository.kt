package com.tafakkur.blogweb.repository

import com.tafakkur.blogweb.core.sealed.ApiResponse
import com.tafakkur.blogweb.dto.*

typealias ApiPostResponse = ApiResponse<Response<PostResponse>>
typealias ApiDashboardResponse = ApiResponse<Response<DashboardResponse>>
typealias ApiPostsResponse = ApiResponse<Response<List<PostResponse>>>
interface PostRepository {
    suspend fun createPost(postRequest: PostRequest, thumbnailImage: ByteArray?): ApiPostResponse
    suspend fun updatePost(id: Long, postRequest: PostRequest, thumbnailImage: ByteArray?): ApiPostResponse
    suspend fun getAllPosts(filter: MutableMap<String, Any>? = mutableMapOf()): ApiPostsResponse
    suspend fun getPostDetail(id: Long): ApiPostResponse

    suspend fun getAllFrontPosts(filter: MutableMap<String, Any>? = mutableMapOf()): ApiPostsResponse
    suspend fun getFrontPostDetail(id: Long): ApiPostResponse

    suspend fun getPostDashboard(): ApiDashboardResponse
}