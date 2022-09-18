package com.phixlab.flowardblog.presentation.users_usecase

import UserPost
import Users
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.phixlab.flowardblog.R
import com.phixlab.flowardblog.databinding.UserListItemBinding

class UserAdapter(
    private var mList: List<Users>,
    private var context: Context,
) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private lateinit var mListener: OnItemListener
    private var mPostList: List<UserPost>? = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = UserListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = mList[position]
        holder.bindViews(user, context, mPostList!!)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun updateUserAdapterWithPost(posts: List<UserPost>) {
        mPostList = posts
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemListener) {
        mListener = listener
    }

    interface OnItemListener {
        fun onItemClick(position: Int)
    }


    class ViewHolder(private var viewBinding: UserListItemBinding, listener: OnItemListener) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bindViews(user: Users, context: Context, userPost: List<UserPost>) {

            // get post for this user to pull its count
            val posts = userPost.filter {
                it.userId == user.userId
            }
            if (user.thumbnailUrl != null) {
                Glide.with(context)
                    .load(user.url)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .fallback(R.drawable.ic_launcher_background)
                    .into(viewBinding.userPhoto)
            } else {
                viewBinding.userPhoto.setImageResource(R.drawable.ic_launcher_background)
            }
            viewBinding.userName.text = user.name
            viewBinding.postCount.text = posts.size.toString()
        }

        init {
            viewBinding.root.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition)
            }
        }
    }
}