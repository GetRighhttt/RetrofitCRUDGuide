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

    private operator fun MutableLiveData<List<Post>>.invoke(post: List<Post>) =
        _posts.postValue(post)

    /*
    Live Data value to note if loading list or not
     */
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private operator fun MutableLiveData<Boolean>.invoke(state: Boolean) =
        _isLoading.postValue(state)

    /*
    Live Data for error message.
     */
    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    private operator fun MutableLiveData<String?>.invoke(message: String?) =
        _errorMessage.postValue(message)

    private infix fun launchViewModelScope(block: suspend () -> Unit) =
        viewModelScope.launch { block() }

    /*
    Maintain state of query parameters for post list
     */
    private var currentPage = 1

    /*
    Used in UI layer with Coroutines for asynchronous programming.
     */
    val getPosts: () -> Unit = {
        launchViewModelScope {

            _isLoading(true) // set loading value to true before getting posts.
            _errorMessage(null) // set initial error message value

            try {
                val fetchedPosts = RetrofitInstance.api.getPosts(currentPage)
                currentPage += 1 // increment current page each time.
                val currentPosts = _posts.value ?: emptyList()
                _posts(currentPosts + fetchedPosts) // sets livedata value.
            } catch (e: Exception) {
                _errorMessage(e.message)
                Log.e(TAG, "Exception $e")
            } finally {
                _isLoading(false) // set to false when finished fetching posts.
            }
        }
    }
}