package com.example.carsfans.data.network.impl


import com.example.carsfans.data.network.CarsApiService
import com.example.carsfans.data.network.NetworkClient
import com.example.carsfans.data.network.dto.CarInfoDto
import com.example.carsfans.data.network.dto.CarResponseEntity
import com.example.carsfans.data.network.dto.PostResponseEntity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NetworkClientImpl : NetworkClient {

    private val baseUrl = "http://am111.05.testing.place/api/v1/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: CarsApiService by lazy {
        retrofit.create(CarsApiService::class.java)
    }

    override suspend fun getCarsList(): List<CarInfoDto> {
        val result = retrofitService.getCarsList()
        return result.body()!!
    }

    override suspend fun getPostsList(id: Int): PostResponseEntity {
        val result = retrofitService.getPostsList(id)
        return result.body()!!
    }

    override suspend fun getCarInfo(id: Int): CarResponseEntity {
        val result = retrofitService.getCarInfo(id)
        return result.body()!!
    }

}