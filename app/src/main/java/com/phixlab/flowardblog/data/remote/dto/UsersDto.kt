package com.phixlab.flowardblog.data.remote.dto

import com.squareup.moshi.Json


data class UsersDto(

    @field:Json(name = "albumId") val albumId: Int? = null,
    @field:Json(name = "userId") val userId: Int? = null,
    @field:Json(name = "name") val name: String? = null,
    @field:Json(name = "url") val url: String? = null,
    @field:Json(name = "thumbnailUrl") val thumbnailUrl: String? = null

)