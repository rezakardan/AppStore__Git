package com.example.appstore.data.model.detail


import com.google.gson.annotations.SerializedName

data class ResponseFavoriteDetail(
    @SerializedName("count")
    val count: Int?, // 1
    @SerializedName("message")
    val message: String? // add_favorite
)