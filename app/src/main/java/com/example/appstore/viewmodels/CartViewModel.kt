package com.example.appstore.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appstore.data.model.SimpleResponse
import com.example.appstore.data.model.cart.BodyAddToCart
import com.example.appstore.data.model.cart.ResponseCartContinue
import com.example.appstore.data.model.cart.ResponseGetCartList
import com.example.appstore.data.model.cart.ResponseUpdateCart
import com.example.appstore.data.repository.CartRepository
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CartViewModel@Inject constructor(private val repository: CartRepository):ViewModel() {



    val addToCartLiveData= MutableLiveData<NetworkRequest<SimpleResponse>>()
    fun callAddToCartApi(id:Int,body: BodyAddToCart)=viewModelScope.launch {

        addToCartLiveData.value= NetworkRequest.Loading()

        val response=repository.postAddToCart(id,body)

        addToCartLiveData.value= NetworkResponse(response).generateResponse()



    }





    val getCartListLiveData=MutableLiveData<NetworkRequest<ResponseGetCartList>>()

fun getCartList()=viewModelScope.launch {

    getCartListLiveData.value=NetworkRequest.Loading()

    val response=repository.getCartList()

    getCartListLiveData.value=NetworkResponse(response).generateResponse()







}


    val incrementCartLiveData=MutableLiveData<NetworkRequest<ResponseUpdateCart>>()
    fun callIncrementCartApi(id:Int)=viewModelScope.launch {

        incrementCartLiveData.value=NetworkRequest.Loading()

       val response=repository.putIncrementCart(id)

        incrementCartLiveData.value=NetworkResponse(response).generateResponse()








    }


    val decrementCartLiveData=MutableLiveData<NetworkRequest<ResponseUpdateCart>>()
    fun callDecrementCartApi(id:Int)=viewModelScope.launch {

        decrementCartLiveData.value=NetworkRequest.Loading()

        val response=repository.putDecrementCart(id)

        decrementCartLiveData.value=NetworkResponse(response).generateResponse()








    }


    val deleteCartLiveData=MutableLiveData<NetworkRequest<ResponseUpdateCart>>()
    fun callDeleteCartApi(id:Int)=viewModelScope.launch {

        deleteCartLiveData.value=NetworkRequest.Loading()

        val response=repository.deleteProductFromCart(id)

        deleteCartLiveData.value=NetworkResponse(response).generateResponse()








    }



    val cartContinueLiveData=MutableLiveData<NetworkRequest<ResponseCartContinue>>()
    fun callCartContinueApi()=viewModelScope.launch {

        cartContinueLiveData.value=NetworkRequest.Loading()

        val response=repository.cartContinue()

        cartContinueLiveData.value=NetworkResponse(response).generateResponse()



    }

}