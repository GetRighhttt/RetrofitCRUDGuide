package com.example.retrofitindepthguide.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitindepthguide.api.RetrofitInstance
import com.example.retrofitindepthguide.model.Post
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _posts: MutableLiveData<List<Post>> = MutableLiveData()
    val posts: LiveData<List<Post>> get() = _posts
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> get() = _errorMessage
    private var currentPage = 1

    fun fetchPosts() {
        viewModelScope.launch {
            _isLoading.value = true

            runCatching {
                try {
                    val fetchedPosts = RetrofitInstance.api.getPosts(currentPage)

                    //setup custom pagination
                    val currentPosts = _posts.value ?: emptyList()
                    currentPage += 1
                    _posts.value = currentPosts + fetchedPosts
                } catch (e: CancellationException) {
                    Log.e(TAG, "Coroutine Cancelled")
                    throw e
                } catch (e: Exception) {
                    Log.e(TAG, "Exception $e")
                    _errorMessage.value = e.message
                } finally {
                    _isLoading.value = false
                }
            }.onFailure { throwable ->
                Log.e(TAG, "Failed to fetch posts", throwable)
            }
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}