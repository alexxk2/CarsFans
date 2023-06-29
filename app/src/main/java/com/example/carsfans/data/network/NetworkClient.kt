package com.example.carsfans.data.network

import com.example.carsfans.data.network.dto.CarInfoDto
import com.example.carsfans.data.network.dto.CarResponseEntity
import com.example.carsfans.data.network.dto.PostResponseEntity


interface NetworkClient {

    suspend fun getCarsList(): List<CarInfoDto>
    suspend fun getPostsList(id: Int): PostResponseEntity
    suspend fun getCarInfo(id: Int): CarResponseEntity
}