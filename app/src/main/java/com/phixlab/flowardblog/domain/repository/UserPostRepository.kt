package com.phixlab.flowardblog.domain.repository


import UserPost
import Users
import com.phixlab.flowardblog.data.common.ApiResponseState
import kotlinx.coroutines.flow.Flow

interface UserPostRepository {
    suspend fun getUserPosts(): Flow<ApiResponseState<List<UserPost>>>
    suspend fun getUsers(): Flow<ApiResponseState<List<Users>>>
}