package com.example.carsfans.domain.network

import com.example.carsfans.domain.models.PostInfo
import com.example.carsfans.domain.repositories.NetworkRepository

class GetPostsListUseCase(private val networkRepository: NetworkRepository) {

    suspend fun execute(id: Int): List<PostInfo> = networkRepository.getPostsList(id)
}