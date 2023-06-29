package com.example.carsfans.domain.network

import com.example.carsfans.domain.models.CarInfo
import com.example.carsfans.domain.repositories.NetworkRepository

class GetCarsListUseCase(private val networkRepository: NetworkRepository) {

    suspend fun execute(): List<CarInfo> = networkRepository.getCarsList()
}