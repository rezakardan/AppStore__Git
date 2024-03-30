package com.example.appstore.data.repository

import com.example.appstore.data.server.ApiService
import javax.inject.Inject

class CategoryRepository@Inject constructor(private val api:ApiService) {


    suspend fun getCategory()=api.getCategory()
}