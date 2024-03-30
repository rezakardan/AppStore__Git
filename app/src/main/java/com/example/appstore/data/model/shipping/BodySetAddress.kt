package com.example.appstore.data.model.shipping


import com.google.gson.annotations.SerializedName

data class BodySetAddress(
    @SerializedName("addressId")
    var addressId: String?=null // 90
)