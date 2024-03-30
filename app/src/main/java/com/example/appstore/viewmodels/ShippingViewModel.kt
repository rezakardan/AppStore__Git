package com.example.appstore.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appstore.data.model.shipping.*
import com.example.appstore.data.model.wallet.ResponseIncreaseWallet
import com.example.appstore.data.repository.ShippingRepository
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShippingViewModel@Inject constructor(private val repository: ShippingRepository):ViewModel() {


    val shippingLiveData=MutableLiveData<NetworkRequest<ResponseShippingList>>()
    fun callShippingApi()=viewModelScope.launch {

        shippingLiveData.value=NetworkRequest.Loading()

        val response=repository.getShippingList()

        shippingLiveData.value=NetworkResponse(response).generateResponse()




    }




    fun callSetAddressApi(body:BodySetAddress)=viewModelScope.launch {


        repository.setAddress(body)



    }

    val couponLiveData=MutableLiveData<NetworkRequest<ResponseCouponShipping>>()

    fun callCouponShippingApi(body:BodyCouponShipping)=viewModelScope.launch {

        couponLiveData.value=NetworkRequest.Loading()

        val response=repository.couponShipping(body)

        couponLiveData.value=NetworkResponse(response).generateResponse()


    }


    val paymentLiveData=MutableLiveData<NetworkRequest<ResponseIncreaseWallet>>()

    fun callPaymentApi(body: BodyCouponShipping)=viewModelScope.launch {

        paymentLiveData.value=NetworkRequest.Loading()

        val response=repository.payment(body)

        paymentLiveData.value=NetworkResponse(response).generateResponse()


    }


}