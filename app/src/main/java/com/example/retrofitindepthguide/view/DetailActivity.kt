package com.example.retrofitindepthguide.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitindepthguide.R
import com.example.retrofitindepthguide.databinding.ActivityDetailBinding
import com.example.retrofitindepthguide.databinding.ActivityMainBinding
import com.example.retrofitindepthguide.viewmodel.DetailViewModel
import com.example.retrofitindepthguide.viewmodel.MainViewModel

private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
       Creates view model instance
        */
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        /*
        Retrieve postID from Main Activity
         */
        val postID = intent.getIntExtra(EXTRA_POST_ID, -1)

        /*
        Methods to observe live data changes.
         */
        viewModel.post.observe(this, Observer { post ->
            binding.apply {
                idBody.text = "Post ${post.id}"
                titleBody.text = post.title
                blogBody.text = post.body
            }
        })

        viewModel.isLoading.observe(this, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.clContent.visibility = if (isLoading) View.GONE else View.VISIBLE
        })
    }
}