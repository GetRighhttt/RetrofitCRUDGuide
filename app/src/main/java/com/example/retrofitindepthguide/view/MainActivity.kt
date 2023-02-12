package com.example.retrofitindepthguide.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitindepthguide.R
import com.example.retrofitindepthguide.databinding.ActivityMainBinding
import com.example.retrofitindepthguide.model.Post
import com.example.retrofitindepthguide.view.adapter.BlogPostAdapter
import com.example.retrofitindepthguide.viewmodel.MainViewModel

private const val TAG = "MainActivity"

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
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        /*
        Methods to observe live data changes.
         */
        viewModel.posts.observe(this, Observer { posts ->
            blogPosts.addAll(posts) // update live data with posts observer
            blogPostAdapter.notifyDataSetChanged() // notify adapter data has changed.
        })

        viewModel.isLoading.observe(this, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        /*
        set the adapter, and layout manager
         */
        blogPostAdapter = BlogPostAdapter(this, blogPosts)
        binding.rvPosts.adapter = blogPostAdapter
        binding.rvPosts.layoutManager = LinearLayoutManager(this)

        /*
        button to fetch posts.
         */
        binding.button.setOnClickListener {
            viewModel.getPosts()
        }
    }
}