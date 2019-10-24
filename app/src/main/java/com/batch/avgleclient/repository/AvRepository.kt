package com.batch.avgleclient.repository

import com.batch.avgleclient.model.AvgleApi
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AvRepository {
    fun getRetrofit() = Retrofit.Builder()
        .baseUrl(AvgleApi.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
        .build()
        .create(AvgleApi::class.java)
}