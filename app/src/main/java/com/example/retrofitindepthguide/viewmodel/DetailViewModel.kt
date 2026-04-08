package com.example.retrofitindepthguide.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitindepthguide.api.RetrofitInstance.api
import com.example.retrofitindepthguide.model.Post
import com.example.retrofitindepthguide.model.User
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    private val _post: MutableLiveData<Post> = MutableLiveData()
    val post: LiveData<Post> get() = _post
    private val _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> get() = _user
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getPostsDetails(postId: Int?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val post = api.getPost(postId ?: 0)
                val userDeferred = async {
                    api.getUser(post.id ?: 0)
                }
                _post.value = post
                _user.value = userDeferred.await()
            } catch (e: Exception) {
                if (e is CancellationException) throw e
            } catch (e: Exception) {
                Log.e("CHECK", "Failed to fetch post details", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}