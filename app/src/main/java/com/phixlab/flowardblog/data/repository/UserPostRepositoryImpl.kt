package com.phixlab.flowardblog.data.repository

import UserPost
import Users
import com.phixlab.flowardblog.data.common.ApiResponseState
import com.phixlab.flowardblog.data.remote.UserPostApiService
import com.phixlab.flowardblog.data.toUserPost
import com.phixlab.flowardblog.data.toUsers
import com.phixlab.flowardblog.domain.repository.UserPostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserPostRepositoryImpl(private val api: UserPostApiService) : UserPostRepository {
    override suspend fun getUserPosts(): Flow<ApiResponseState<List<UserPost>>> {
        return flow {
            val results = api.getUserPosts()
            val postListData = results.map { postDto ->
                postDto.toUserPost()
            }
            emit(ApiResponseState.success(postListData))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUsers(): Flow<ApiResponseState<List<Users>>> {
        return flow<ApiResponseState<List<Users>>> {
            val results = api.getUsers()
            val userListData = results.map { userDto ->
                userDto.toUsers()
            }
            emit(ApiResponseState.success(userListData))
        }.flowOn(Dispatchers.IO)
    }


}