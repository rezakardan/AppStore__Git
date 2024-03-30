package com.example.appstore.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appstore.data.model.favorite.ResponseDeleteFavorite
import com.example.appstore.data.model.favorite.ResponseGetFavorite
import com.example.appstore.data.repository.FavoriteRepository
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel@Inject constructor(private val repository: FavoriteRepository):ViewModel() {


    val getFavoriteLiveData=MutableLiveData<NetworkRequest<ResponseGetFavorite>>()

    fun getFavorites()=viewModelScope.launch {


        getFavoriteLiveData.value=NetworkRequest.Loading()


        val response=repository.getFavorite()

        getFavoriteLiveData.value=NetworkResponse(response).generateResponse()



    }


    val deleteFavorite=MutableLiveData<NetworkRequest<ResponseDeleteFavorite>>()

    fun callDeleteFavoriteApi(favoriteId:Int)=viewModelScope.launch {


       deleteFavorite.value= NetworkRequest.Loading()


        val response=repository.deleteFavorite(favoriteId)

        deleteFavorite.value=NetworkResponse(response).generateResponse()








    }


}