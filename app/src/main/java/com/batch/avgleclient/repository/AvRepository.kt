package com.batch.avgleclient.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.batch.avgleclient.AvgleApi
import com.batch.avgleclient.model.AvCategory
import com.batch.avgleclient.model.AvCollection
import com.batch.avgleclient.model.AvVideo
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AvRepository constructor(baseURL: String) : AvgleApi {
    val apiClient = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
        .build()
        .create(AvgleApi::class.java)

    override suspend fun getAvCategories(): AvCategory {
        return apiClient.getAvCategories()
    }

    override suspend fun getAvCollections(page: Int): AvCollection {
        return apiClient.getAvCollections(1)
    }

    override suspend fun getAvVideos(page: Int): AvVideo {
        return apiClient.getAvVideos(0)
    }

    override suspend fun searchAv(query: String, page: Int): AvVideo {
        return apiClient.searchAv("紗倉まな", 0)
    }




}