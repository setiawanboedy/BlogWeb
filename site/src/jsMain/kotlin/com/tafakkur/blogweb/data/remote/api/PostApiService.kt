package com.tafakkur.blogweb.data.remote.api

import com.tafakkur.blogweb.config.Config
import com.tafakkur.blogweb.core.storage.LocalStorageManager
import com.tafakkur.blogweb.core.utils.Constants.AUTH_TOKEN
import com.tafakkur.blogweb.dto.PostRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.koin.core.Koin
import org.koin.core.context.GlobalContext.get

class PostApiService(private val client: HttpClient) {
    private var token = ""
    init {
        val inject: Koin = get()
        val storage = inject.get<LocalStorageManager>()
        token = storage.getItem(AUTH_TOKEN).toString()

    }
    suspend fun createPost(postRequest: PostRequest, thumbnailImage: ByteArray?): HttpResponse{
        return client.submitFormWithBinaryData(
            url = "${Config.BASE_URL}api/posts/create",
            formData = formData {
                append("title", postRequest.title)
                append("subtitle", postRequest.subtitle)
                append("content", postRequest.content)
                append("category", postRequest.category)
                append("tags", JSON.stringify(postRequest.tags))
                append("main", postRequest.main.toString())
                append("popular", postRequest.popular.toString())
                append("sponsored", postRequest.sponsored.toString())
                append("status", postRequest.status)
                if (postRequest.thumbnailLinkUrl.isNotEmpty()){
                    append("thumbnailLinkUrl", postRequest.thumbnailLinkUrl)
                }

                if (thumbnailImage != null) {
                    append("thumbnailImageUrl", thumbnailImage, Headers.build {
                        append(HttpHeaders.ContentDisposition, "filename=\"${postRequest.thumbnailName}\"")
                    })
                }
            }
        ) {
            header("Authorization", "Bearer $token")
        }
    }

    suspend fun updatePost(id: Long, postRequest: PostRequest, thumbnailImage: ByteArray?): HttpResponse{
        return client.submitFormWithBinaryData(
            url = "${Config.BASE_URL}api/posts/$id/update",
            formData = formData {
                append("title", postRequest.title)
                append("subtitle", postRequest.subtitle)
                append("content", postRequest.content)
                append("category", postRequest.category)
                append("main", postRequest.main.toString())
                append("popular", postRequest.popular.toString())
                append("sponsored", postRequest.sponsored.toString())
                append("tags", JSON.stringify(postRequest.tags))
                append("status", postRequest.status)
                if (postRequest.thumbnailLinkUrl.isNotEmpty()){
                    append("thumbnailLinkUrl", postRequest.thumbnailLinkUrl)
                }

                if (thumbnailImage != null) {
                    append("thumbnailImageUrl", thumbnailImage, Headers.build {
                        append(HttpHeaders.ContentDisposition, "filename=\"${postRequest.thumbnailName}\"")
                    })
                }
            }
        ) {
            header("Authorization", "Bearer $token")
        }
    }

    suspend fun getAllPosts(filter: MutableMap<String, Any>?): HttpResponse{
        return client.get("${Config.BASE_URL}api/posts/list") {

            filter?.entries?.forEach { param ->
                parameter(param.key, param.value)
            }
            header("Authorization", "Bearer $token")
        }
    }

    suspend fun getAllFrontPosts(filter: MutableMap<String, Any>?): HttpResponse{
        return client.get("${Config.BASE_URL}api/front/posts/list") {

            filter?.entries?.forEach { param ->
                parameter(param.key, param.value)
            }
        }
    }

    suspend fun getPostDetail(id: Long): HttpResponse{
        return client.get("${Config.BASE_URL}api/posts/$id/detail") {
            header("Authorization", "Bearer $token")
        }
    }

    suspend fun getFrontPostDetail(id: Long): HttpResponse{
        return client.get("${Config.BASE_URL}api/front/posts/$id/detail") {
        }
    }

    suspend fun getPostDashboard(): HttpResponse{
        return client.get("${Config.BASE_URL}api/posts/dashboard") {
            header("Authorization", "Bearer $token")
        }
    }
}