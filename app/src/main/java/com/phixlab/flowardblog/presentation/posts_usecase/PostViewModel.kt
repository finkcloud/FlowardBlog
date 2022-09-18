package com.phixlab.flowardblog.presentation.posts_usecase

import UserPost
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phixlab.flowardblog.data.common.ApiResponseState
import com.phixlab.flowardblog.data.common.Status
import com.phixlab.flowardblog.data.remote.UserPostApiService
import com.phixlab.flowardblog.data.repository.UserPostRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {

    private val repositoryImpl = UserPostRepositoryImpl(api = UserPostApiService.userPostApiService())

    val postState = MutableStateFlow(ApiResponseState(Status.LOADING, listOf(UserPost()), ""))

    fun getUserPost() {
        postState.value = ApiResponseState.loading()

        viewModelScope.launch {
            repositoryImpl.getUserPosts()
                .catch { postState.value = ApiResponseState.error(it.message.toString()) }
                .collect {
                    postState.value = ApiResponseState.success(it.data)
                }
        }

    }
}