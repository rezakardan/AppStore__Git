package com.example.appstore.data.model.favorite


import com.google.gson.annotations.SerializedName

data class ResponseDeleteFavorite(
    @SerializedName("message")
    val message: String? // success
)