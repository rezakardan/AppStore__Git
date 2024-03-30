package com.example.appstore.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appstore.data.model.profile_order.ResponseProfileOrders
import com.example.appstore.data.repository.ProfileOrdersRepository
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileOrderViewModel@Inject constructor(private val repository:ProfileOrdersRepository):ViewModel() {



val profileOrderLiveData=MutableLiveData<NetworkRequest<ResponseProfileOrders>>()
fun callProfileOrderApi(status:String)=viewModelScope.launch {

    profileOrderLiveData.value=NetworkRequest.Loading()

    val response=repository.profileOrders(status)

    profileOrderLiveData.value=NetworkResponse(response).generateResponse()






}

}