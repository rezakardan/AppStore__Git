package com.example.appstore.data.repository

import com.example.appstore.data.model.shipping.BodyCouponShipping
import com.example.appstore.data.model.shipping.BodySetAddress
import com.example.appstore.data.server.ApiService
import javax.inject.Inject

class ShippingRepository@Inject constructor(private val api:ApiService) {

    suspend fun getShippingList()=api.getShippingList()

suspend fun setAddress(body:BodySetAddress)=api.setAddress(body)

    suspend fun couponShipping(body:BodyCouponShipping)=api.callCouponShippingApi(body)

    suspend fun payment(body: BodyCouponShipping)=api.postPayment(body)
}