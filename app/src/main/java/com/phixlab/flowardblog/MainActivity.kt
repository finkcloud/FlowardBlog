package com.phixlab.flowardblog

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phixlab.flowardblog.data.common.Status
import com.phixlab.flowardblog.databinding.ActivityMainBinding
import com.phixlab.flowardblog.presentation.posts_usecase.PostViewModel
import com.phixlab.flowardblog.presentation.users_usecase.UserAdapter
import com.phixlab.flowardblog.presentation.users_usecase.UsersViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var userViewModel: UsersViewModel
    private lateinit var postViewModel: PostViewModel
    private lateinit var binding: ActivityMainBinding

    private var recyclerView: RecyclerView? = null
    private var manager: LinearLayoutManager? = null
    private var adapter: UserAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        manager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = manager


        userViewModel = ViewModelProvider(this)[UsersViewModel::class.java]
        postViewModel = ViewModelProvider(this)[PostViewModel::class.java]

        userViewModel.getUsers()
        postViewModel.getUserPost()

        setSupportActionBar(binding.toolbar)

        updateUI()

    }

    private fun updateUI() {

        //users
        lifecycleScope.launch {
            userViewModel.userState.collect { response ->
                when (response.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        response.data?.let { list ->
                            getPostAndUpdateCount()   // update post to sync with user
                            adapter =
                                UserAdapter(list, this@MainActivity)
                            recyclerView!!.adapter = adapter
                            adapter?.setOnItemClickListener(object : UserAdapter.OnItemListener {
                                override fun onItemClick(position: Int) {
                                    val user = list[position]
                                    val userId = user.userId
                                    val photoUrl = user.url

                                    openPostActivity(userId, photoUrl)

                                }

                            })


                        }
                    }
                    else -> {
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(this@MainActivity, "${response.message}", Toast.LENGTH_LONG)
                            .show()
                    }

                }
            }
        }

    }

    private fun getPostAndUpdateCount() {
        //posts
        lifecycleScope.launch {
            postViewModel.postState.collect {
                when (it.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        it.data?.let { postList ->
                            adapter?.updateUserAdapterWithPost(postList)
                        }
                    }
                    else -> {
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(applicationContext, "", Toast.LENGTH_LONG).show()
                    }

                }
            }
        }
    }

    private fun openPostActivity(userId: Int?, userPhoto: String?) {
        val intent = Intent(this, PostActivity::class.java).apply {
            putExtra("user_id", userId)
            putExtra("user_photo_url", userPhoto)
        }
        startActivity(intent)
    }


}

