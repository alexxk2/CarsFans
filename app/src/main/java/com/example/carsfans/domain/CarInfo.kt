package com.example.carsfans.domain

data class CarInfo(
    val id: Int,
    val brandName: String,
    val modelName: String,
    val engineName: String,
    val transmissionName: String,
    val engineVolume: String,
    val year: Int,
    val imageSrc: String
)
