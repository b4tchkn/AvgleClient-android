package com.batch.avgleclient.model

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface AvgleApi {
    @GET("categories")
    fun getAvCategoriesAsync(): Deferred<Response<List<AvCategory>>>
}