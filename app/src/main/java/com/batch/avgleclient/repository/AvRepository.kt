package com.batch.avgleclient.repository

import com.batch.avgleclient.AvgleApi
import com.batch.avgleclient.model.AvCategory
import com.batch.avgleclient.model.AvCollection
import com.batch.avgleclient.model.AvVideo
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AvRepository constructor(baseURL: String) : AvgleApi {
    private val okHttp = OkHttpClient()
        .newBuilder()
        .addNetworkInterceptor(StethoInterceptor())
        .build()

    private val apiClient = Retrofit.Builder()
        .client(okHttp)
        .baseUrl(baseURL)
        .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
        .build()
        .create(AvgleApi::class.java)

    override suspend fun getAvCategories(): AvCategory {
        return apiClient.getAvCategories()
    }

    override suspend fun getAvCollections(page: Int): AvCollection {
        return apiClient.getAvCollections(page)
    }

    override suspend fun getAvVideos(page: Int): AvVideo {
        return apiClient.getAvVideos(page)
    }

    override suspend fun searchAv(query: String, page: Int): AvVideo {
        return apiClient.searchAv(query, page)
    }


}