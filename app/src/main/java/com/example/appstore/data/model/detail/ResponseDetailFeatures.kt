package com.example.appstore.data.model.detail


import com.google.gson.annotations.SerializedName

class ResponseDetailFeatures : ArrayList<ResponseDetailFeatures.ResponseDetailFeaturesItem>(){
    data class ResponseDetailFeaturesItem(
        @SerializedName("featureItem_title")
        val featureItemTitle: String?, // 6.0 اینچ و بزرگتر
        @SerializedName("feature_title")
        val featureTitle: String? // اندازه صفحه نمایش
    )
}