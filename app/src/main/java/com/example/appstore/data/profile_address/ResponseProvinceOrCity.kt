package com.example.appstore.data.profile_address


import com.google.gson.annotations.SerializedName

class ResponseProvinceOrCity : ArrayList<ResponseProvinceOrCity.ResponseProvinceOrCityItem>(){
    data class ResponseProvinceOrCityItem(
        @SerializedName("id")
        val id: Int?, // 1
        @SerializedName("title")
        val title: String? // آذربایجان شرقی
    )
}