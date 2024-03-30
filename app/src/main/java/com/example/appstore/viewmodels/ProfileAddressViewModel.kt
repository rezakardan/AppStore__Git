package com.example.appstore.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appstore.data.model.comment.ResponseDeleteComments
import com.example.appstore.data.profile_address.BodyResponseInsertOrUpdate
import com.example.appstore.data.profile_address.ResponseInsertOrUpdateAddress
import com.example.appstore.data.profile_address.ResponseProfileAddresses
import com.example.appstore.data.profile_address.ResponseProvinceOrCity
import com.example.appstore.data.repository.ProfileAddressesRepository
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileAddressViewModel @Inject constructor(private val repository: ProfileAddressesRepository) :
    ViewModel() {

        val addressLiveData=MutableLiveData<NetworkRequest<ResponseProfileAddresses>>()

    fun callAddressesApi()=viewModelScope.launch {

        addressLiveData.value=NetworkRequest.Loading()


        val response=repository.getProfileAddresses()

        addressLiveData.value=NetworkResponse(response).generateResponse()




    }




    val provinceLiveData=MutableLiveData<NetworkRequest<ResponseProvinceOrCity>>()
fun callProvinceAddressApi()=viewModelScope.launch {

    provinceLiveData.value=NetworkRequest.Loading()

    val response=repository.getProvincesListAddress()

    provinceLiveData.value=NetworkResponse(response).generateResponse()






}




    val cityLiveData=MutableLiveData<NetworkRequest<ResponseProvinceOrCity>>()
    fun callCityApi(provinceId:Int)=viewModelScope.launch {

        cityLiveData.value=NetworkRequest.Loading()

        val response=repository.getCityListAddress(provinceId)

        cityLiveData.value=NetworkResponse(response).generateResponse()


    }



    val insertOrUpdateAddressLiveData=MutableLiveData<NetworkRequest<ResponseInsertOrUpdateAddress>>()

    fun callInsertOrUpdateAddressApi(body:BodyResponseInsertOrUpdate)=viewModelScope.launch {

        insertOrUpdateAddressLiveData.value=NetworkRequest.Loading()

        val response=repository.postInsertOrUpdateAddress(body)

        insertOrUpdateAddressLiveData.value=NetworkResponse(response).generateResponse()

    }



    val deleteAddressLiveData=MutableLiveData<NetworkRequest<ResponseDeleteComments>>()
fun callDeleteAddressApi(address_Id:Int)=viewModelScope.launch {

    deleteAddressLiveData.value=NetworkRequest.Loading()

    val response=repository.deleteAddress(address_Id)

    deleteAddressLiveData.value=NetworkResponse(response).generateResponse()





}
}