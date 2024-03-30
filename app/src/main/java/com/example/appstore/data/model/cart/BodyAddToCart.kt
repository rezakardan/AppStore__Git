package com.example.appstore.data.model.cart


import com.google.gson.annotations.SerializedName

data class BodyAddToCart(
    @SerializedName("colorId")
    var colorId: String?=null // 2
)