package com.phixlab.flowardblog.data.remote


import com.phixlab.flowardblog.data.remote.dto.UserPostDto
import com.phixlab.flowardblog.data.remote.dto.UsersDto
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface UserPostApiService {

    @GET("SharminSirajudeen/test_resources/users")
    suspend fun getUsers(): List<UsersDto>
    // debug use
    @GET("SharminSirajudeen/test_resources/posts")
    suspend fun getUserPostsById(@Query("userId") id: Int): List<UserPostDto>

    @GET("SharminSirajudeen/test_resources/posts")
    suspend fun getUserPosts(): List<UserPostDto>


  companion  object AppConfiguration {
        private const val BASE_URL = "https://my-json-server.typicode.com/"

        fun userPostApiService(): UserPostApiService {
            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(UserPostApiService::class.java)
        }
    }
}