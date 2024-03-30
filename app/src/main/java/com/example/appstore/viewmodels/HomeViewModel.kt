package com.example.appstore.viewmodels

import androidx.lifecycle.*
import com.example.appstore.data.model.home.ResponseBanners
import com.example.appstore.data.model.home.ResponseDiscount
import com.example.appstore.data.model.home.ResponseProducts
import com.example.appstore.data.model.profile.ResponseProfile
import com.example.appstore.data.repository.HomeRepository
import com.example.appstore.utils.*
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {


    val imgProfileLiveData = MutableLiveData<NetworkRequest<ResponseProfile>>()
    fun callImgProfileApi() = viewModelScope.launch {


        imgProfileLiveData.value = NetworkRequest.Loading()

        val response = repository.imgProfile()

        imgProfileLiveData.value = NetworkResponse(response).generateResponse()


    }


    val bannersHomeLiveData = MutableLiveData<NetworkRequest<ResponseBanners>>()
    fun callBannersApi() = viewModelScope.launch {

        bannersHomeLiveData.value = NetworkRequest.Loading()

        val response = repository.getBannersListHome(GENERAL)
        bannersHomeLiveData.value = NetworkResponse(response).generateResponse()


    }


    val discountLiveData = MutableLiveData<NetworkRequest<ResponseDiscount>>()
    fun callDiscountApi() = viewModelScope.launch {

        discountLiveData.value = NetworkRequest.Loading()

        val response = repository.getDiscount(ELECTRONICDEVICES)

        discountLiveData.value = NetworkResponse(response).generateResponse()


    }


    val mobileLiveData = MutableLiveData<NetworkRequest<ResponseProducts>>()


    fun callMobileApi() = viewModelScope.launch {

        mobileLiveData.value = NetworkRequest.Loading()

        val response = repository.getMobilesList(CATEGORY_MOBILE_PHONE, NEW)

        mobileLiveData.value = NetworkResponse(response).generateResponse()


    }


    val shoesLiveData = MutableLiveData<NetworkRequest<ResponseProducts>>()

    fun callShoesApi() = viewModelScope.launch {

        shoesLiveData.value = NetworkRequest.Loading()


        val response = repository.getShoesList(CATEGORY_MEN_SHOES, NEW)

        shoesLiveData.value = NetworkResponse(response).generateResponse()


    }



    val lapTopLiveData = MutableLiveData<NetworkRequest<ResponseProducts>>()

    fun callLapTopApi() = viewModelScope.launch {

        lapTopLiveData.value = NetworkRequest.Loading()


        val response = repository.getLapTopList(CATEGORY_LAPTOP, NEW)

        lapTopLiveData.value = NetworkResponse(response).generateResponse()


    }



    val stationaryLiveData = MutableLiveData<NetworkRequest<ResponseProducts>>()

    fun callStationaryApi() = viewModelScope.launch {

        stationaryLiveData.value = NetworkRequest.Loading()


        val response = repository.getStationaryList(CATEGORY_STATIONERY, NEW)

        stationaryLiveData.value = NetworkResponse(response).generateResponse()


    }



}