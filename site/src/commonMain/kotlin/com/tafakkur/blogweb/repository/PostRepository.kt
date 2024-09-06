package com.tafakkur.blogweb.repository

import com.tafakkur.blogweb.core.sealed.ApiResponse
import com.tafakkur.blogweb.dto.*

typealias ApiPostResponse = ApiResponse<Response<PostResponse>>
interface PostRepository {
    suspend fun createPost(postRequest: PostRequest, thumbnailImage: ByteArray?): ApiPostResponse
}