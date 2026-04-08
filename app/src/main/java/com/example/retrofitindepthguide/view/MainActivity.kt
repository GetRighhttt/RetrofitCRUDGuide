package com.example.retrofitindepthguide.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitindepthguide.databinding.ActivityMainBinding
import com.example.retrofitindepthguide.model.Post
import com.example.retrofitindepthguide.view.adapter.BlogPostAdapter
import com.example.retrofitindepthguide.viewmodel.MainViewModel

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
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setUpObservers()
        setAdapterOnClick()
        setAdapterInstance()
        setOnClickForPost()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpObservers() {
        viewModel.posts.observe(this@MainActivity) { posts ->
            val numberElements = blogPosts.size
            blogPosts.clear()
            blogPosts.addAll(posts)
            blogPostAdapter.notifyDataSetChanged()
            scrollSmooth(numberElements)
        }

        viewModel.isLoading.observe(this@MainActivity) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(this@MainActivity) { errorMessage ->
            if (errorMessage == null) {
                binding.tvError.visibility = View.GONE
            } else {
                binding.tvError.visibility = View.VISIBLE
                Toast.makeText(this@MainActivity, "Error loading posts", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun scrollSmooth(position: Int) = binding.rvPosts.smoothScrollToPosition(position)

    private fun setOnClickForPost() {
        binding.button.setOnClickListener {
            viewModel.fetchPosts()
        }
    }

    private fun setAdapterOnClick() {
        blogPostAdapter = BlogPostAdapter(
            context = this,
            posts = blogPosts,
            itemClickListener = object : BlogPostAdapter.ItemClickListener {
                override fun onItemClick(post: Post) {
                    createItemIntent(post)
                }
            })
    }

    private fun createItemIntent(post: Post) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(EXTRA_POST_ID, post.id)
        startActivity(intent)
    }

    private fun setAdapterInstance() {
        binding.rvPosts.adapter = blogPostAdapter
        binding.rvPosts.layoutManager = LinearLayoutManager(this)
    }

    companion object {
        const val EXTRA_POST_ID = "EXTRA_POST_ID"
    }
}