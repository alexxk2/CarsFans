package com.example.carsfans.domain.network

import com.example.carsfans.domain.models.SingleCar
import com.example.carsfans.domain.repositories.NetworkRepository

class GetCarInfoUseCase(private val networkRepository: NetworkRepository) {

    suspend fun execute(id: Int): SingleCar = networkRepository.getCarInfo(id)
}