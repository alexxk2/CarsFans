package com.example.carsfans.domain.models

data class SingleCar(
    val id: Int,
    val brandName: String,
    val modelName: String,
    val engineName: String,
    val transmissionName: String,
    val engineVolume: String,
    val year: Int,
    val imageSrc: String,
    val avatarImageSrc: String,
    val accountName: String
)
