package com.phixlab.flowardblog.presentation.users_usecase

import Users
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phixlab.flowardblog.data.common.ApiResponseState
import com.phixlab.flowardblog.data.common.Status
import com.phixlab.flowardblog.data.remote.UserPostApiService
import com.phixlab.flowardblog.data.repository.UserPostRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class UsersViewModel : ViewModel() {
    private val repositoryImpl = UserPostRepositoryImpl(api = UserPostApiService.userPostApiService())

    val userState = MutableStateFlow(ApiResponseState(Status.LOADING, listOf(Users()), ""))



    fun getUsers() {
        userState.value = ApiResponseState.loading()
        viewModelScope.launch {
            repositoryImpl.getUsers()
                .catch {
                    userState.value = ApiResponseState.error(it.message.toString())
                }
                .collect {
                    userState.value = ApiResponseState.success(it.data)
                }
        }

    }
}