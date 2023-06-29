package com.example.carsfans.di

import com.example.carsfans.domain.network.GetCarInfoUseCase
import com.example.carsfans.domain.network.GetCarsListUseCase
import com.example.carsfans.domain.network.GetPostsListUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<GetCarsListUseCase> {GetCarsListUseCase(networkRepository = get()) }

    factory<GetPostsListUseCase> {GetPostsListUseCase(networkRepository = get()) }

    factory<GetCarInfoUseCase> {GetCarInfoUseCase(networkRepository = get()) }

}