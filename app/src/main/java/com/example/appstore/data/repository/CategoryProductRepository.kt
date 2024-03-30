package com.example.appstore.data.repository

import com.example.appstore.data.server.ApiService
import javax.inject.Inject

class CategoryProductRepository @Inject constructor(private val api: ApiService) {


    suspend fun categoryProduct(
        slug: String, search: String?=null, sort: String?=null, minPrice: String?=null, maxPrice: String?=null,
        selectedBrands: String?=null, selectedColors: String?=null, page: String?="1", onlyAvailable: Boolean?=null,
    ) = api.getProductCategory( slug, search, sort, minPrice, maxPrice,
        selectedBrands, selectedColors, page, onlyAvailable)


}