package com.batch.avgleclient.model

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface AvgleApi {
    companion object {
        const val BASE_URL = "https://api.avgle.com/v1/"
    }
    @GET("/categories")
    suspend fun getAvCategories(): Deferred<Response<List<AvCategory>>>
}