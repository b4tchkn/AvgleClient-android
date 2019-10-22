package com.batch.avgleclient.model

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CategoryRepository {
    fun getCategory() = Retrofit.Builder()
        .baseUrl(AvgleApi.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build().create(AvgleApi::class.java)
}