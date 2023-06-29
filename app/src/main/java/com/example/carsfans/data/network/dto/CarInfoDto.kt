package com.example.carsfans.data.network.dto

import com.google.gson.annotations.SerializedName



data class CarInfoDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("brand_name")
    val brandName: String,
    @SerializedName("model_name")
    val modelName: String,
    @SerializedName("engine_name")
    val engineName: String,
    @SerializedName("transmission_name")
    val transmissionName: String,
    @SerializedName("engine_volume")
    val engineVolume: String,
    @SerializedName("year")
    val year: Int,
    @SerializedName("image")
    val imageSrc: String
)