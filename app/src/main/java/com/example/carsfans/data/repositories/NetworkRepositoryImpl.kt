package com.example.carsfans.data.repositories

import com.example.carsfans.data.network.NetworkClient
import com.example.carsfans.data.network.dto.CarInfoDto
import com.example.carsfans.data.network.dto.CarResponseEntity
import com.example.carsfans.data.network.dto.PostInfoDto
import com.example.carsfans.domain.models.CarInfo
import com.example.carsfans.domain.models.PostInfo
import com.example.carsfans.domain.models.SingleCar
import com.example.carsfans.domain.repositories.NetworkRepository

class NetworkRepositoryImpl(private val networkClient: NetworkClient) : NetworkRepository {


    override suspend fun getCarsList(): List<CarInfo> {
        val result = networkClient.getCarsList()
        return result.map {
            mapCarInfoToDomain(it)
        }
    }

    override suspend fun getPostsList(id: Int): List<PostInfo> {
        val result = networkClient.getPostsList(id)
        return result.posts.map {
            mapPostInfoToDomain(it)
        }
    }

    override suspend fun getCarInfo(id: Int): SingleCar {
        val result = networkClient.getCarInfo(id)
        return mapCarSingeInfoToDomain(result)
    }


    private fun mapCarInfoToDomain(carInfoDto: CarInfoDto): CarInfo {
        with(carInfoDto) {
            return CarInfo(
                id = id,
                brandName = brandName,
                modelName = modelName,
                engineName = engineName,
                transmissionName = transmissionName,
                engineVolume = engineVolume,
                year = year,
                imageSrc = imageSrc
            )
        }
    }

    private fun mapPostInfoToDomain(postInfoDto: PostInfoDto): PostInfo {
        with(postInfoDto) {
            return PostInfo(
                id = id,
                postText = postText,
                likeCount = likeCount,
                commentCount = commentCount,
                imageSrc = imageSrc
            )
        }
    }

    private fun mapCarSingeInfoToDomain(carResponseEntity: CarResponseEntity): SingleCar {
        with(carResponseEntity) {
            return SingleCar(
                id = car.id,
                brandName = car.brandName,
                modelName = car.modelName,
                engineName = car.engineName,
                transmissionName = car.transmissionName,
                engineVolume = car.engineVolume,
                year = car.year,
                imageSrc = car.imageSrc.first().url,
                avatarImageSrc = user.avatar.url,
                accountName = user.username
            )
        }
    }

}