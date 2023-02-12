package com.example.retrofitindepthguide.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Another way of creating a retrofit instance.
 */
private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

object RetrofitInstance {

    /*
    Do not need to create object until it is accessed so we initialize it as by lazy
     */
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    /*
    Publicly available to be called by other classes, etc.
     */
    val api: BlogApi by lazy { retrofit.create(BlogApi::class.java) }
}