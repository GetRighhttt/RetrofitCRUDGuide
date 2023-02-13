package com.example.retrofitindepthguide.api

import com.example.retrofitindepthguide.model.Post
import com.example.retrofitindepthguide.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BlogApi {

    /*
    Coroutine style:
    GET method for a list of posts. We add in two Query parameters for filtering the data
    based on what we want.
     */
    @GET("posts")
    suspend fun getPosts(
        @Query("_page") page: Int = 1,
        @Query("_limit") limit: Int = 10
    ): List<Post>

    // Coroutine style to get post by id
    @GET("posts/{id}")
    suspend fun getPost(@Path("id") postId: Int): Post

    // Coroutine style to get users by id
    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: Int): User

    // Callback style to get post by ID
    @GET("posts/{id}")
    fun getPostUsingCallbacks(@Path("id") postId: Int): Call<Post>

    // Callback style to get user by ID
    @GET("users/{id}")
    fun getUserUsingCallbacks(@Path("id") userId: Int): Call<User>

}