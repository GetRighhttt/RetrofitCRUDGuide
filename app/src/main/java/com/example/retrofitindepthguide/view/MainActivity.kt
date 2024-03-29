package com.example.retrofitindepthguide.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitindepthguide.databinding.ActivityMainBinding
import com.example.retrofitindepthguide.model.Post
import com.example.retrofitindepthguide.view.adapter.BlogPostAdapter
import com.example.retrofitindepthguide.viewmodel.MainViewModel
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"
const val EXTRA_POST_ID = "EXTRA_POST_ID"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var blogPostAdapter: BlogPostAdapter
    private val blogPosts = mutableListOf<Post>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        Creates view model instance
         */
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // live data instances and variables instances to invoke.
        observePostsLiveData.invoke()
        observeIsLoadingLiveData.invoke()
        errorPostLiveData.invoke()
        setAdapterOnClick.invoke()
        setAdapterInstance.invoke()
        setOnClickForPost.invoke()
    }

    // variable for smooth scrolling
    private val scrollSmooth: (Int) -> Unit =
        { position -> binding.rvPosts.smoothScrollToPosition(position) }

    private val setOnClickForPost: () -> Unit = {
        binding.button.setOnClickListener {
            lifecycleScope.launch {
                //get more posts to add to the list
                viewModel.fetchPosts()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private val observePostsLiveData: () -> Unit = {
        lifecycleScope.launch {
            viewModel.posts.observe(this@MainActivity) { posts ->
                val numberElements = blogPosts.size
                blogPosts.clear()
                blogPosts.addAll(posts)
                blogPostAdapter.notifyDataSetChanged()
                scrollSmooth(numberElements)
            }
        }
    }

    private val observeIsLoadingLiveData: () -> Unit = {
        lifecycleScope.launch {
            viewModel.isLoading.observe(this@MainActivity) { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    private val errorPostLiveData: () -> Unit = {
        lifecycleScope.launch {
            viewModel.errorMessage.observe(this@MainActivity) { errorMessage ->
                if (errorMessage == null) {
                    binding.tvError.visibility = View.GONE
                } else {
                    binding.tvError.visibility = View.VISIBLE
                    Toast.makeText(this@MainActivity, "Error loading posts", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private val setAdapterOnClick: () -> Unit = {
        blogPostAdapter =
            BlogPostAdapter(this, blogPosts, object : BlogPostAdapter.ItemClickListener {
                override fun onItemClick(post: Post) {
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                    intent.putExtra(EXTRA_POST_ID, post.id)
                    startActivity(intent)
                }
            })
    }

    private val setAdapterInstance: () -> Unit = {
        binding.rvPosts.adapter = blogPostAdapter
        binding.rvPosts.layoutManager = LinearLayoutManager(this)
    }
}