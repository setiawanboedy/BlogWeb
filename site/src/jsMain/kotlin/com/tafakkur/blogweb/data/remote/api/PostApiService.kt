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
    var token = ""
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
                append("status", postRequest.status)
                if (postRequest.thumbnailLinkUrl.isNotEmpty()){
                    append("thumbnailLinkUrl", postRequest.thumbnailLinkUrl)
                }

                if (thumbnailImage != null) {
                    append("thumbnailImageUrl", thumbnailImage, Headers.build {
                        append(HttpHeaders.ContentDisposition, "filename=\"thumbnail.png\"")
                    })
                }
            }
        ) {
            header("Authorization", "Bearer $token")
        }
    }

    suspend fun getAllPosts(filter: MutableMap<String, Any>): HttpResponse{
        return client.get("${Config.BASE_URL}api/posts/list") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
        }
    }
}