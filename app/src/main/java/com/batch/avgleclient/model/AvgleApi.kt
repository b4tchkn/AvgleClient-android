package com.batch.avgleclient.model

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AvgleApi {
    companion object {
        const val BASE_URL = "https://api.avgle.com/v1/"
    }
    @GET("categories")
    suspend fun getAvCategories(): AvCategory

    @GET("collections/{page}")
    suspend fun getAvCollections(
        @Path("page") page: Int
    ): AvCollection
}