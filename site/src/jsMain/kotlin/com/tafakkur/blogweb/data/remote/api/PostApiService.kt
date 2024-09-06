package com.tafakkur.blogweb.data.remote.api

import com.tafakkur.blogweb.config.Config
import com.tafakkur.blogweb.core.storage.LocalStorageManager
import com.tafakkur.blogweb.core.utils.Constants.AUTH_TOKEN
import com.tafakkur.blogweb.dto.PostRequest
import io.ktor.client.*
import io.ktor.client.request.*
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
    suspend fun createPost(postRequest: PostRequest): HttpResponse{
        return client.post("${Config.BASE_URL}api/posts/create"){
            header("Authorization", "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(postRequest)
        }
    }
}