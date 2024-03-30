package com.example.appstore.data.repository

import com.example.appstore.data.server.ApiService
import javax.inject.Inject

class FavoriteRepository@Inject constructor(private val api:ApiService) {

    suspend fun getFavorite()=api.getFavorites()
    suspend fun deleteFavorite(favoriteId:Int)=api.deleteFavorites(favoriteId)

}