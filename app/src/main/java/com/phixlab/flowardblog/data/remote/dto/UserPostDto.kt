package com.phixlab.flowardblog.data.remote.dto

import com.squareup.moshi.Json


data class UserPostDto(

    @field:Json(name = "userId") val userId: Int? = null,
    @field:Json(name = "id") val id: Int? = null,
    @field:Json(name = "title") val title: String? = null,
    @field:Json(name = "body") val body: String? = null

)
