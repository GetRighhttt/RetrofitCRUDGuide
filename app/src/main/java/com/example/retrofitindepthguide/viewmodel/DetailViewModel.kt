package com.example.retrofitindepthguide.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitindepthguide.api.RetrofitInstance
import com.example.retrofitindepthguide.model.Post
import com.example.retrofitindepthguide.model.User
import kotlinx.coroutines.launch

class DetailViewModel: ViewModel() {
    /*
   Variable to get the list of Post with backing property
    */
    private val _posts: MutableLiveData<Post> = MutableLiveData()
    val post: LiveData<Post>
        get() = _posts

    /*
  Variable to get the list of Post with backing property
   */
    private val _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User>
        get() = _user

    /*
    Live Data value to note if loading list or not
     */
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    /*
    Used in UI layer with Coroutines for asynchronous programming.
     */
    fun getPostsDetails(postId: Int) {
        val api = RetrofitInstance.api
      viewModelScope.launch {
          _isLoading.value = true
          val fetchedPost = api.getPost(postId)
          val fetchedUser = api.getUser(fetchedPost.userID)
          _posts.value = fetchedPost
          _user.value = fetchedUser
          _isLoading.value = false
      }
    }
}