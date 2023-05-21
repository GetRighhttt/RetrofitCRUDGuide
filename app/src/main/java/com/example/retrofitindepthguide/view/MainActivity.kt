package com.example.retrofitindepthguide.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitindepthguide.databinding.ActivityMainBinding
import com.example.retrofitindepthguide.model.Post
import com.example.retrofitindepthguide.view.adapter.BlogPostAdapter
import com.example.retrofitindepthguide.viewmodel.MainViewModel

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

        /*
        Methods to observe live data changes.

        We do a little pagination here where when we click the get posts button again,
        it adds on more posts to the end of the list.
         */
        viewModel.posts.observe(this) { posts ->
            val numberElements = blogPosts.size
            blogPosts.clear()
            blogPosts.addAll(posts)
            blogPostAdapter.notifyDataSetChanged()
            binding.rvPosts.smoothScrollToPosition(numberElements)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage == null) {
                binding.tvError.visibility = View.GONE
            } else {
                binding.tvError.visibility = View.VISIBLE
                Toast.makeText(this, "Error loading posts", Toast.LENGTH_LONG).show()
            }
        }

        /*
        set the adapter, and layout manager
         */
        blogPostAdapter =
            BlogPostAdapter(this, blogPosts, object : BlogPostAdapter.ItemClickListener {
                override fun onItemClick(post: Post) {
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                    intent.putExtra(EXTRA_POST_ID, post.id)
                    startActivity(intent)
                }
            })

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