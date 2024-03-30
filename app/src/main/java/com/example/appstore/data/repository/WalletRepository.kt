package com.example.appstore.data.repository

import com.example.appstore.data.model.wallet.BodyIncreaseWallet
import com.example.appstore.data.server.ApiService
import javax.inject.Inject

class WalletRepository@Inject constructor(private val api:ApiService) {

    suspend fun getWallet()=api.getWallet()

suspend fun postWallet(body:BodyIncreaseWallet)=api.postIncreaseWallet(body)

}