package com.korea200.utils

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitInstance {
    val server: Server by lazy {
        val json = Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        }
        Retrofit.Builder()
            .baseUrl("https://korea200server.onrender.com/")
            .addConverterFactory(
                json.asConverterFactory("application/json; charset=UTF-8".toMediaType() ) )
            .build()
            .create(Server::class.java)
    }
}