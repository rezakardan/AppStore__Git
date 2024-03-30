package com.example.appstore.data.repository

import com.example.appstore.data.profile_address.BodyResponseInsertOrUpdate
import com.example.appstore.data.server.ApiService
import javax.inject.Inject

class ProfileAddressesRepository @Inject constructor(private val api: ApiService) {

    suspend fun getProfileAddresses() = api.profileAddresses()

    suspend fun getProvincesListAddress() = api.getProvinceAddress()

    suspend fun getCityListAddress(provinceId: Int) = api.getCityAddress(provinceId)

    suspend fun postInsertOrUpdateAddress(body: BodyResponseInsertOrUpdate) =
        api.insertOrUpdateAddress(body)

    suspend fun deleteAddress(address_Id: Int) = api.deleteAddress(address_Id)

}