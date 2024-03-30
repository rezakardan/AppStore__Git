package com.example.appstore.data.repository

import com.example.appstore.data.server.ApiService
import javax.inject.Inject

class ProfileOrdersRepository@Inject constructor(private val api:ApiService) {


suspend fun profileOrders(status:String)=api.profileOrders(status)



}