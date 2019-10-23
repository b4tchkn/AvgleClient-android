package com.batch.avgleclient.repository

//import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.batch.avgleclient.model.AvgleApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CategoryRepository {
    fun getCategory() = Retrofit.Builder()
        .baseUrl(AvgleApi.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build().create(AvgleApi::class.java)
}