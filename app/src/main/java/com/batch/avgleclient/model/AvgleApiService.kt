package com.batch.avgleclient.model

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AvgleApiService {
    private val BASE_URL = "https://api.avgle.com/v1/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build().create(AvgleApi::class.java)

//    fun getAvCategories(): Deferred<Response<List<AvCategory>>> {
//        return api.getAvCategories()
//    }

    fun getAvCategories() = api.getAvCategories()
}