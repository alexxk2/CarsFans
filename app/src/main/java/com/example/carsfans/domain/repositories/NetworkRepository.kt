package com.example.carsfans.domain.repositories

import com.example.carsfans.domain.models.SingleCar
import com.example.carsfans.domain.models.CarInfo
import com.example.carsfans.domain.models.PostInfo

interface NetworkRepository {

    suspend fun getCarsList(): List<CarInfo>
    suspend fun getPostsList(id: Int): List<PostInfo>
    suspend fun getCarInfo(id: Int): SingleCar
}