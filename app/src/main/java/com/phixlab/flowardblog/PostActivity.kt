package com.phixlab.flowardblog

import UserPost
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.phixlab.flowardblog.data.common.Status
import com.phixlab.flowardblog.databinding.ActivityPostBinding
import com.phixlab.flowardblog.presentation.posts_usecase.PostAdapter
import com.phixlab.flowardblog.presentation.posts_usecase.PostViewModel
import kotlinx.coroutines.launch

class PostActivity : AppCompatActivity() {

    private lateinit var postViewModel: PostViewModel

    private var posts: List<UserPost>? = null
    private var userId: Int? = null

    private var recyclerView: RecyclerView? = null
    private var manager: LinearLayoutManager? = null
    private var adapter: PostAdapter? = null
    private lateinit var binding: ActivityPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.postRecyclerView
        manager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = manager

        postViewModel = ViewModelProvider(this)[PostViewModel::class.java]

        setSupportActionBar(binding.toolbar)

        userId = intent.getIntExtra("user_id", 1) // for filtering users post
        postViewModel.getUserPost()// get posts ready for this user
        updatedUI() // update user's post


        val userPhoto = intent.getStringExtra("user_photo_url")
        if (userPhoto != null) {
            Glide.with(applicationContext)
                .load(userPhoto)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .fallback(R.drawable.ic_launcher_background)
                .into(binding.userPostPhoto)
        } else {
            binding.userPostPhoto.setImageResource(R.drawable.ic_launcher_background)
        }


    }

    private fun updatedUI() {
        lifecycleScope.launch {
            postViewModel.postState.collect {
                when (it.status) {
                    Status.LOADING -> {
                        binding.progressCircularPost.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircularPost.visibility = View.GONE
                        it.data?.let { postList ->
                            // get post for the selected user and render
                            posts = postList.filter { post ->
                                post.userId == userId
                            }
                            // populatr  list
                            adapter =
                                PostAdapter(posts!!, applicationContext)
                            recyclerView!!.adapter = adapter


                        }
                    }
                    else -> {
                        binding.progressCircularPost.visibility = View.GONE
                        Toast.makeText(applicationContext, "", Toast.LENGTH_LONG).show()
                    }

                }
            }
        }
    }


}