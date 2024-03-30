package com.example.appstore.data.model.login


import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("ex")
    val ex: Int?, // 4
    @SerializedName("isEmail")
    val isEmail: Int?, // 0
    @SerializedName("time")
    val time: Int? // 90
)