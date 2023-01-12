package com.example.retrofitindepthguide.api

import com.example.retrofitindepthguide.model.Post
import retrofit2.http.GET

interface BlogApi {

    @GET("posts")
    suspend fun getPosts(): List<Post>
}