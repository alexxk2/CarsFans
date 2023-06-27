package com.example.carsfans.domain

data class PostInfo(
    val id: Int,
    val postText: String,
    val likeCount: Int,
    val commentCount: Int,
    val imageSrc: String
)
