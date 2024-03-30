package com.example.appstore.data.repository

import com.example.appstore.data.model.cart.BodyAddToCart
import com.example.appstore.data.server.ApiService
import javax.inject.Inject

class CartRepository@Inject constructor(private val api:ApiService) {



    suspend fun postAddToCart(id:Int,body: BodyAddToCart)=api.addToCartDetail(id,body)




    suspend fun getCartList()=api.getCartList()
    suspend fun putIncrementCart(id: Int)=api.putIncrementCart(id)
    suspend fun putDecrementCart(id:Int)=api.putDecrementCart(id)

    suspend fun deleteProductFromCart(id:Int)=api.deleteProductFromCart(id)

    suspend fun cartContinue()=api.cartContinue()
}