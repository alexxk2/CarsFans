package com.example.carsfans.di

import com.example.carsfans.presentation.info.view_model.InfoViewModel
import com.example.carsfans.presentation.list.view_model.ListVIewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel<ListVIewModel> { ListVIewModel(getCarsListUseCase = get()) }

    viewModel<InfoViewModel> {
        InfoViewModel(
            getCarInfoUseCase = get(),
            getPostsListUseCase = get()
        )
    }
}