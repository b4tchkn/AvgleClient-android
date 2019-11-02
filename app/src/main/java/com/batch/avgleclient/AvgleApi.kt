package com.batch.avgleclient

import android.provider.Settings.System.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.batch.avgleclient.model.AvCategory
import com.batch.avgleclient.model.AvCollection
import com.batch.avgleclient.model.AvVideo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AvgleApi {
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

    @GET("search/{query}/{page}")
    suspend fun searchAv(
        @Path("query") query: String,
        @Path("page") page: Int
    ): AvVideo
}