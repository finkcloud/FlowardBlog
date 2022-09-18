package com.phixlab.flowardblog.presentation.posts_usecase

import UserPost
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.phixlab.flowardblog.databinding.PostListItemBinding

class PostAdapter(private var postList: List<UserPost>, private var context: Context) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PostListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = postList[position]
        holder.bindView(post)
    }

    override fun getItemCount(): Int {
        return postList.size
    }


    class ViewHolder(private var viewBinding: PostListItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bindView(post: UserPost) {
            viewBinding.postTitle.text = post.title
            viewBinding.postBody.text = post.body
        }

    }
}