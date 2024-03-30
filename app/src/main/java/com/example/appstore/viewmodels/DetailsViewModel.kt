package com.example.appstore.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appstore.data.model.SimpleResponse
import com.example.appstore.data.model.cart.BodyAddToCart
import com.example.appstore.data.model.detail.*
import com.example.appstore.data.repository.DetailRepository
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel@Inject constructor(private val repository:DetailRepository):ViewModel() {


    val detailLiveData=MutableLiveData<NetworkRequest<ResponseDetail>>()

    fun callDetailApi(id:Int)=viewModelScope.launch {


        detailLiveData.value=NetworkRequest.Loading()

        val response=repository.getDetailProducts(id)

        detailLiveData.value=NetworkResponse(response).generateResponse()
    }



    val productIdLiveData=MutableLiveData<Int>()
    fun sendProductId(id:Int)=viewModelScope.launch {

        productIdLiveData.value=id


    }



    val favoriteLiveData=MutableLiveData<NetworkRequest<ResponseFavoriteDetail>>()
fun callFavoriteApi(id:Int)=viewModelScope.launch {


    favoriteLiveData.value=NetworkRequest.Loading()

    val response=repository.postFavoriteDetail(id)
    favoriteLiveData.value=NetworkResponse(response).generateResponse()

}

    val featuresLiveData=MutableLiveData<NetworkRequest<ResponseDetailFeatures>>()
    fun detailFeatures(id:Int)=viewModelScope.launch {

        featuresLiveData.value=NetworkRequest.Loading()


        val response=repository.detailFeatures(id)

        featuresLiveData.value=NetworkResponse(response).generateResponse()


    }


    val detailCommentLiveData=MutableLiveData<NetworkRequest<ResponseGetDetailComment>>()

    fun detailComments(id:Int)=viewModelScope.launch {

        detailCommentLiveData.value=NetworkRequest.Loading()

        val response=repository.getDetailComment(id)

        detailCommentLiveData.value=NetworkResponse(response).generateResponse()




    }



    val detailAddCommentLiveData=MutableLiveData<NetworkRequest<SimpleResponse>>()

    fun postDetailAddComments(id:Int,body:BodyDetailAddComment)=viewModelScope.launch {

        detailAddCommentLiveData.value=NetworkRequest.Loading()

        val response=repository.postDetailAddComments(id,body)

        detailAddCommentLiveData.value=NetworkResponse(response).generateResponse()





    }




    val detailChartLiveData=MutableLiveData<NetworkRequest<ResponseDetailPriceChart>>()

    fun detailPriceChart(id:Int)=viewModelScope.launch {

        detailChartLiveData.value=NetworkRequest.Loading()

        val response=repository.detailPriceChart(id)


        detailChartLiveData.value=NetworkResponse(response).generateResponse()



    }







}