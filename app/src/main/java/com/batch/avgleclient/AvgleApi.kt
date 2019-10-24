package com.batch.avgleclient

import com.batch.avgleclient.model.AvCategory
import com.batch.avgleclient.model.AvCollection
import com.batch.avgleclient.model.AvVideo
import retrofit2.http.GET
import retrofit2.http.Path

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

    @GET("videos/{page}")
    suspend fun getAvVideos(
        @Path("page") page: Int
    ): AvVideo
}