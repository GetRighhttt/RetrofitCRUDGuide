package com.example.retrofitindepthguide.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitindepthguide.R
import com.example.retrofitindepthguide.model.Post

class BlogPostAdapter(
    private val context: Context,
    private val posts: List<Post>,
    private val itemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<BlogPostAdapter.ViewHolder>() {

    interface ItemClickListener {
        fun onItemClick(post: Post)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvId = itemView.findViewById<TextView>(R.id.id_body)
        private val tvTitle = itemView.findViewById<TextView>(R.id.title_body)
        private val tvPost = itemView.findViewById<TextView>(R.id.blog_Body)

        @SuppressLint("SetTextI18n")
        fun bind(post: Post) {
            tvId.text = "Post #${post.id}"
            tvTitle.text = post.title
            tvPost.text = post.body
            itemView.setOnClickListener {
                itemClickListener.onItemClick(post)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int = posts.size
}