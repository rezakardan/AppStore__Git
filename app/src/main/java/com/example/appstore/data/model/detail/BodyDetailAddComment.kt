package com.example.appstore.data.model.detail


import com.google.gson.annotations.SerializedName

data class BodyDetailAddComment(
    @SerializedName("comment")
    var comment: String?=null, // محصول با کیفیتیه
    @SerializedName("rate")
    var rate: String?=null // 3
)