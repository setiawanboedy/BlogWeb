package com.tafakkur.blogweb.data.remote.repository

import com.tafakkur.blogweb.core.sealed.ApiResponse
import com.tafakkur.blogweb.core.storage.LocalStorageManager
import com.tafakkur.blogweb.core.utils.Constants.AUTH_TOKEN
import com.tafakkur.blogweb.core.utils.Constants.EXPIRES_AT
import com.tafakkur.blogweb.data.remote.api.AuthApiService
import com.tafakkur.blogweb.data.remote.api.PostApiService
import com.tafakkur.blogweb.dto.*
import com.tafakkur.blogweb.repository.*
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*


class PostRepositoryImpl(
    private val postApiService: PostApiService
) : PostRepository {

    override suspend fun createPost(postRequest: PostRequest,thumbnailImage: ByteArray?): ApiPostResponse {
        return try {
            val response = postApiService.createPost(postRequest, thumbnailImage)
            if (response.status.isSuccess()) {
                ApiResponse.Success(response.body())

            } else {
                ApiResponse.Error("Error: ${response.bodyAsText()}")
            }
        } catch (e: Exception) {
            ApiResponse.Error("Exception: ${e.message}")
        }
    }

    override suspend fun updatePost(id: Long, postRequest: PostRequest, thumbnailImage: ByteArray?): ApiPostResponse {
        return try {
            val response = postApiService.updatePost(id, postRequest, thumbnailImage)
            if (response.status.isSuccess()) {
                ApiResponse.Success(response.body())

            } else {
                ApiResponse.Error("Error: ${response.bodyAsText()}")
            }
        } catch (e: Exception) {
            ApiResponse.Error("Exception: ${e.message}")
        }
    }

    override suspend fun getAllPosts(filter: MutableMap<String, Any>): ApiPostsResponse {
        return try {
            val response = postApiService.getAllPosts(filter)
            if (response.status.isSuccess()){
                ApiResponse.Success(response.body())
            }else{
                ApiResponse.Error("Error: ${response.bodyAsText()}")
            }
        }catch (e: Exception){
            ApiResponse.Error("Exception: ${e.message}")
        }
    }

    override suspend fun getPostDetail(id: Long): ApiPostResponse {
        return try {
            val response = postApiService.getPostDetail(id)
            if (response.status.isSuccess()){
                ApiResponse.Success(response.body())
            }else{
                ApiResponse.Error("Error: ${response.bodyAsText()}")
            }
        }catch (e: Exception){
            ApiResponse.Error("Exception: ${e.message}")
        }
    }

}