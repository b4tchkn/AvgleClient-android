package com.batch.avgleclient.model

import android.util.Log
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

    fun getAvCategoriesAsync(): Deferred<Response<List<AvCategory>>> {
        Log.d("batchTag", "suspendの中")
        return api.getAvCategoriesAsync()
    }

//    fun getAvCategories() = api.getAvCategories()
}