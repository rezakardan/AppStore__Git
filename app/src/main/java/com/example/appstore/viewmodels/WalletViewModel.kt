package com.example.appstore.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appstore.data.model.wallet.BodyIncreaseWallet
import com.example.appstore.data.model.wallet.ResponseGetWallet
import com.example.appstore.data.model.wallet.ResponseIncreaseWallet
import com.example.appstore.data.repository.WalletRepository
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WalletViewModel @Inject constructor(private val repository: WalletRepository) : ViewModel() {

    val walletLiveData = MutableLiveData<NetworkRequest<ResponseGetWallet>>()

    fun getWalletApi() = viewModelScope.launch {


        walletLiveData.value = NetworkRequest.Loading()

        val response = repository.getWallet()

        walletLiveData.value = NetworkResponse(response).generateResponse()


    }


    val increaseWalletLiveData = MutableLiveData<NetworkRequest<ResponseIncreaseWallet>>()
    fun callIncreaseWalletApi(body: BodyIncreaseWallet) = viewModelScope.launch {

        increaseWalletLiveData.value=NetworkRequest.Loading()


        val response=repository.postWallet(body)

        increaseWalletLiveData.value=NetworkResponse(response).generateResponse()







    }

}