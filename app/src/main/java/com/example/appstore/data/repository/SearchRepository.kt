package com.example.appstore.data.repository

import com.example.appstore.data.server.ApiService
import javax.inject.Inject

class SearchRepository@Inject constructor(private val api:ApiService) {



    suspend fun getSearch(q:String,sort:String)=api.getSearch(q, sort)




}