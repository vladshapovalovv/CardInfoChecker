package com.example.binviewapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkLayer {

    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://lookup.binlist.net/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val cardInfoService: CardInfoService by lazy {
        retrofit.create(CardInfoService::class.java)
    }

    val apiClient = ApiClient(cardInfoService)

}