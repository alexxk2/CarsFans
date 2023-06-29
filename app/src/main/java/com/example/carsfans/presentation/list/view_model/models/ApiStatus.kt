package com.example.carsfans.presentation.list.view_model.models

sealed interface ApiStatus {
    object Loading : ApiStatus
    object Done : ApiStatus
    object Error : ApiStatus
}