package com.example.carsfans.di

import com.example.carsfans.data.network.NetworkClient
import com.example.carsfans.data.network.impl.NetworkClientImpl
import com.example.carsfans.data.repositories.NetworkRepositoryImpl
import com.example.carsfans.domain.repositories.NetworkRepository
import org.koin.dsl.module

val dataModule = module {

    single<NetworkClient> {NetworkClientImpl() }

    single<NetworkRepository> {NetworkRepositoryImpl(networkClient = get())}
}