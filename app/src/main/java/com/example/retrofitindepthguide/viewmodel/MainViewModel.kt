package com.example.retrofitindepthguide.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitindepthguide.api.RetrofitInstance
import com.example.retrofitindepthguide.model.Post
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {

    /*
    Variable to get the list of Post with backing property
     */
    private val _posts: MutableLiveData<List<Post>> = MutableLiveData()
    val posts: LiveData<List<Post>>
    get() = _posts


    /*
    Live Data value to note if loading list or not
     */
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
    get() = _isLoading

    /*
    Used in UI layer with Coroutines for asynchronous programming.
     */
    fun getPosts() {
        viewModelScope.launch {
            val fetchedPosts = RetrofitInstance.api.getPosts()
            _posts.value = fetchedPosts // sets livedata value.
        }
    }
}