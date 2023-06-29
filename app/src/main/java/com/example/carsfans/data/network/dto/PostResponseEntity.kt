package com.example.carsfans.data.network.dto

import com.google.gson.annotations.SerializedName


data class PostResponseEntity(
    val posts: List<PostInfoDto>
)

data class PostInfoDto(
    val id: Int,
    @SerializedName("text") val postText: String,
    @SerializedName("like_count") val likeCount: Int,
    @SerializedName("comment_count") val commentCount: Int,
    @SerializedName("img") val imageSrc: String?
)