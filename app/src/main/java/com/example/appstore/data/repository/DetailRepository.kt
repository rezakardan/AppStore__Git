package com.example.appstore.data.repository

import com.example.appstore.data.model.cart.BodyAddToCart
import com.example.appstore.data.model.detail.BodyDetailAddComment
import com.example.appstore.data.server.ApiService
import javax.inject.Inject

class DetailRepository@Inject constructor(private val api:ApiService) {

suspend fun getDetailProducts(id:Int)=api.getDetailProducts(id)

suspend fun postFavoriteDetail(id:Int)=api.postFavoriteDetail(id)


    suspend fun detailFeatures(id:Int)=api.detailFeatures(id)

    suspend fun getDetailComment(id:Int)=api.getDetailComment(id)

    suspend fun postDetailAddComments(id:Int,body:BodyDetailAddComment)=api.postDetailAddComment(id,body)

    suspend fun detailPriceChart(id:Int)=api.detailPriceChart(id)
}