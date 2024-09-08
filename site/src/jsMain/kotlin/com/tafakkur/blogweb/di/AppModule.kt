package com.tafakkur.blogweb.di

import com.tafakkur.blogweb.core.storage.LocalStorageManager
import com.tafakkur.blogweb.data.remote.api.AuthApiService
import com.tafakkur.blogweb.data.remote.api.PostApiService
import com.tafakkur.blogweb.data.remote.repository.AuthRepositoryImpl
import com.tafakkur.blogweb.data.remote.repository.PostRepositoryImpl
import com.tafakkur.blogweb.repository.AuthRepository
import com.tafakkur.blogweb.repository.PostRepository
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import io.ktor.serialization.kotlinx.json.*

val appModule = module {
    single { provideHttpClient() }
    single { AuthApiService(get()) }
    single { PostApiService(get()) }
    single { LocalStorageManager() }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<PostRepository> { PostRepositoryImpl(get()) }
}

fun provideHttpClient(): HttpClient {
    return HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            level = LogLevel.BODY
        }
    }
}