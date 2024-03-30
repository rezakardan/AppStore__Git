package com.example.appstore.data.model.comment


import com.google.gson.annotations.SerializedName

data class ResponseDeleteComments(
    @SerializedName("message")
    val message: String? // success
)