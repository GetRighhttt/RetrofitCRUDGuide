package com.example.retrofitindepthguide.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitindepthguide.databinding.ActivityDetailBinding
import com.example.retrofitindepthguide.view.MainActivity.Companion.EXTRA_POST_ID
import com.example.retrofitindepthguide.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        val postID = intent.getIntExtra(EXTRA_POST_ID, -1)
        viewModel.getPostsDetails(postID)
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.post.observe(this, Observer { post ->
            binding.apply {
                detailId.text = post.id.toString()
                detailTitle.text = post.title
                detailBody.text = post.body
            }
        })

        viewModel.user.observe(this, Observer { user ->
            binding.apply {
                tvUserName.text = user?.name.orEmpty()
                tvUserEmail.text = user?.email.orEmpty()
                tvUserName.text = user?.username.orEmpty()
                tvWebsite.text = user?.website.orEmpty()
                tvPhone.text = user?.phone.orEmpty()
            }
        })

        viewModel.isLoading.observe(this, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
    }
}
