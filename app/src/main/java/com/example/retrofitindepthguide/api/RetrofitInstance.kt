package com.example.retrofitindepthguide.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Another way of creating a retrofit instance.
 */
private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

class RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val api: BlogApi by lazy { retrofit.create(BlogApi::class.java) }
}