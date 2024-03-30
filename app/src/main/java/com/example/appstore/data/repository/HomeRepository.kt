package com.example.appstore.data.repository

import com.example.appstore.data.server.ApiService
import javax.inject.Inject

class HomeRepository@Inject constructor(private val api:ApiService) {

    suspend fun imgProfile()=api.getProfile()

suspend fun getBannersListHome(slug:String)=api.getBannersListHome(slug)

    suspend fun getDiscount(slug: String)=api.getDiscount(slug)

    suspend fun getMobilesList(slug:String,sort:String)=api.getMobilesList(slug,sort)

    suspend fun getShoesList(slug:String,sort:String)=api.getShoesList(slug,sort)
    suspend fun getLapTopList(slug:String,sort:String)=api.getLapTopList(slug,sort)
    suspend fun getStationaryList(slug:String,sort:String)=api.getStationaryList(slug,sort)



}