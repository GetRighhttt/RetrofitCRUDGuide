package com.example.retrofitindepthguide.model

import java.io.Serializable

data class Post(
    val userID: Int,
    val id: Int,
    val title: String,
    val body: String
) : Serializable // allows for us to pass in this object as an object to UI
