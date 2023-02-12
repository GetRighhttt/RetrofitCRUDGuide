package com.example.retrofitindepthguide.view.clicklistener

import com.example.retrofitindepthguide.model.Post

interface ItemClickListener {
    fun onItemClick(post: Post)
}