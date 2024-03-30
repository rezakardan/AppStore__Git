package com.example.appstore.data.model.wallet


import com.google.gson.annotations.SerializedName

data class ResponseGetWallet(
    @SerializedName("wallet")
    val wallet: String? // 0
)