package com.example.carsfans.data.network

import com.example.carsfans.data.network.dto.CarInfoDto
import com.example.carsfans.data.network.dto.CarResponseEntity
import com.example.carsfans.data.network.dto.PostResponseEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CarsApiService {

    @GET("cars/list")
    suspend fun getCarsList(): Response<List<CarInfoDto>>

    @GET("car/{id}/posts")
    suspend fun getPostsList(@Path("id") id: Int): Response<PostResponseEntity>

    @GET("car/{id}")
    suspend fun getCarInfo(@Path("id") id: Int): Response<CarResponseEntity>
}