package com.example.retrofitindepthguide.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitindepthguide.api.RetrofitInstance
import com.example.retrofitindepthguide.api.RetrofitInstance.api
import com.example.retrofitindepthguide.model.Post
import com.example.retrofitindepthguide.model.User
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "DetailViewModel"

class DetailViewModel : ViewModel() {
    /*
    Variable to get the list of Post with backing property
    */
    private val _post: MutableLiveData<Post> = MutableLiveData()
    val post: LiveData<Post>
        get() = _post
    private operator fun MutableLiveData<Post>.invoke(post: Post) = _post.postValue(post)

    /*
    Variable to get the list of Post with backing property
   */
    private val _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User>
        get() = _user
    private operator fun MutableLiveData<User>.invoke(user: User) = _user.postValue(user)

    /*
    Live Data value to note if loading list or not
     */
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private operator fun MutableLiveData<Boolean>.invoke(state: Boolean) =
        _isLoading.postValue(state)

    /*
    Used in UI layer with Coroutines for asynchronous programming.
     */
    fun getPostsDetails(postId: Int) {
        // get a reference to our api using composition
        val api = RetrofitInstance.api
        // Coroutine style
        viewModelScope.launch {
            _isLoading(true)
            val fetchedPost = api.getPost(postId)
            val fetchedUser = api.getUser(fetchedPost.id)
            Log.i("CHECK", "User Id is ${fetchedPost.id}")
            _post(fetchedPost)
            _user(fetchedUser)
            _isLoading(false)
        }
    }

    /*
    Method to fetch data via a callback style without Coroutines.
     */
    private fun fetchDataCallbackStyle(postId: Int) {
        val api = RetrofitInstance.api
        _isLoading(true)

        api.getPostUsingCallbacks(postId).enqueue(object : Callback<Post> {
            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.e(TAG, "Error code: $t")
                _isLoading(false)
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    val fetchedPost = response.body()!!
                    _post(fetchedPost)
                    _isLoading(false)
                    // fetch post, then make second api call
                    api.getUserUsingCallbacks(fetchedPost.id).enqueue(object : Callback<User> {

                        override fun onFailure(call: Call<User>, t: Throwable) {
                            Log.e(TAG, "Error code: $t")
                            _isLoading(false)
                        }

                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            if (response.isSuccessful) {
                                _isLoading(false)
                                val fetchedUser = response.body()!!
                                _user(fetchedUser)
                            } // end if
                        }
                    })

                } else { // end if/else onResponse
                    Log.e(TAG, "Error code: ${response.code()}")
                }
            } // end onResponse method
        }) // end Callback block

    } // end fetch with Callback method
}