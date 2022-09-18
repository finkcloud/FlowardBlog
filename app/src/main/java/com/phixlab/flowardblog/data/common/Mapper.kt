package com.phixlab.flowardblog.data

import UserPost
import Users
import com.phixlab.flowardblog.data.remote.dto.UserPostDto
import com.phixlab.flowardblog.data.remote.dto.UsersDto

fun UserPostDto.toUserPost(): UserPost {
    return UserPost(
        userId = userId ?: 0,
        id = id ?: 0,
        title = title ?: "",
        body = body ?: ""
    )
}

fun UsersDto.toUsers(): Users {
    return Users(
        albumId = albumId ?: 0,
        userId = userId ?: 0,
        name = name ?: "",
        url = url ?: "",
        thumbnailUrl = thumbnailUrl ?: ""
    )
}