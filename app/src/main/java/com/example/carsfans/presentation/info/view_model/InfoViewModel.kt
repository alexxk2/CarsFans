package com.example.carsfans.presentation.info.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsfans.domain.models.PostInfo
import com.example.carsfans.domain.models.SingleCar
import com.example.carsfans.domain.network.GetCarInfoUseCase
import com.example.carsfans.domain.network.GetPostsListUseCase
import com.example.carsfans.presentation.list.view_model.models.ApiStatus
import kotlinx.coroutines.launch

class InfoViewModel(
    private val getCarInfoUseCase: GetCarInfoUseCase,
    private val getPostsListUseCase: GetPostsListUseCase
) : ViewModel() {

    private val _postApiStatus = MutableLiveData<ApiStatus>()
    val postApiStatus: LiveData<ApiStatus> = _postApiStatus

    private val _singleCarApiStatus = MutableLiveData<ApiStatus>()
    val singleCarApiStatus: LiveData<ApiStatus> = _singleCarApiStatus

    private val _postsList = MutableLiveData<List<PostInfo>>()
    val postsList: LiveData<List<PostInfo>> = _postsList

    private val _singleCarInfo = MutableLiveData<SingleCar>()
    val singleCarInfo: LiveData<SingleCar> = _singleCarInfo


    fun getPosts(id: Int) {
        _postApiStatus.value = ApiStatus.Loading
        viewModelScope.launch {
            try {
                _postsList.value = getPostsListUseCase.execute(id)
                _postApiStatus.value = ApiStatus.Done
            } catch (e: Exception) {
                _postApiStatus.value = ApiStatus.Error
            }
        }

    }

    fun getSingleCarInfo(id: Int) {
        _singleCarApiStatus.value = ApiStatus.Loading
        viewModelScope.launch {

            try {
                _singleCarInfo.value = getCarInfoUseCase.execute(id)
                _singleCarApiStatus.value = ApiStatus.Done
            } catch (e: Exception) {
                _singleCarApiStatus.value = ApiStatus.Error
            }
        }
    }

    fun showLoading(id: Int) {
        _postApiStatus.value = ApiStatus.Loading
        viewModelScope.launch {
            try {
                getPostsListUseCase.execute(id)
                _postApiStatus.value = ApiStatus.Done
            } catch (e: Exception) {
                _postApiStatus.value = ApiStatus.Error
            }
        }
    }

}