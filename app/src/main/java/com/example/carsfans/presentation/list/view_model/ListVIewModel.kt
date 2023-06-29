package com.example.carsfans.presentation.list.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsfans.domain.models.CarInfo
import com.example.carsfans.domain.network.GetCarsListUseCase
import com.example.carsfans.presentation.list.view_model.models.ApiStatus
import kotlinx.coroutines.launch



class ListVIewModel(
    private val getCarsListUseCase: GetCarsListUseCase
): ViewModel() {

    private val _apiStatus = MutableLiveData<ApiStatus>()
    val apiStatus: LiveData<ApiStatus> = _apiStatus

    private val _listOfCars= MutableLiveData<List<CarInfo>>()
    val listOfCars: LiveData<List<CarInfo>> = _listOfCars


    fun getCarsList(){
        _apiStatus.value = ApiStatus.Loading
        viewModelScope.launch {
            try {
                _listOfCars.value = getCarsListUseCase.execute()
                _apiStatus.value = ApiStatus.Done
            }
            catch (e: Exception){
                _apiStatus.value = ApiStatus.Error
            }
        }
    }

    fun showLoading(){
        _apiStatus.value = ApiStatus.Loading
        viewModelScope.launch {
            try {
                getCarsListUseCase.execute()
                _apiStatus.value = ApiStatus.Done
            }
            catch (e: Exception){
                _apiStatus.value = ApiStatus.Error
            }
        }

    }


}