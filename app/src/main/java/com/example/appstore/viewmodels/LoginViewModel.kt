package com.example.appstore.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appstore.data.model.login.BodyResponseLogin
import com.example.appstore.data.model.login.ResponseLogin
import com.example.appstore.data.model.login.ResponseVerify
import com.example.appstore.data.repository.LoginRepository
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {

    val loginLiveData = MutableLiveData<NetworkRequest<ResponseLogin>>()

    fun callLoginApi(body: BodyResponseLogin) = viewModelScope.launch {

        loginLiveData.value = NetworkRequest.Loading()


        val response = repository.postLogin(body)

        loginLiveData.value = NetworkResponse(response).generateResponse()


    }


    val verifyLiveData = MutableLiveData<NetworkRequest<ResponseVerify>>()


    fun verifyLogin(body: BodyResponseLogin) = viewModelScope.launch {

        verifyLiveData.value = NetworkRequest.Loading()

        val response = repository.postVerify(body)

        verifyLiveData.value = NetworkResponse(response).generateResponse()


    }


}