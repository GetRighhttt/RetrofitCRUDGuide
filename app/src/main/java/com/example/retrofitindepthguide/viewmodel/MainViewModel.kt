package com.example.retrofitindepthguide.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitindepthguide.api.RetrofitInstance
import com.example.retrofitindepthguide.model.Post
import com.example.retrofitindepthguide.model.User
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

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    /*
    Used in UI layer with Coroutines for asynchronous programming.
     */
    fun getPosts() {
        viewModelScope.launch {

            _isLoading.value = true // set loading value to true before getting posts.
            _errorMessage.value = null // set initial error message value

            try {
                val fetchedPosts = RetrofitInstance.api.getPosts()
                _posts.value = fetchedPosts // sets livedata value.
            } catch (e: Exception) {
                _errorMessage.value = e.message
                Log.e(TAG, "Exception $e")
            } finally {
                _isLoading.value = false // set to false when finished fetching posts.
            }
        }
    }
}