package com.example.carsfans.data.network.dto

import com.google.gson.annotations.SerializedName

data class CarResponseEntity(
    val car: Car,
    val user: User
)

data class User(
    val username: String,
    val avatar: Avatar
)

data class Avatar(
    val url: String
)

data class Car(
    @SerializedName("id")
    val id: Int,
    @SerializedName("brand_name")
    val brandName: String,
    @SerializedName("model_name")
    val modelName: String,
    @SerializedName("engine")
    val engineName: String,
    @SerializedName("transmission_name")
    val transmissionName: String,
    @SerializedName("engine_volume")
    val engineVolume: String,
    @SerializedName("year")
    val year: Int,
    @SerializedName("images")
    val imageSrc: List<Image>
)

data class Image(
    val url: String
)