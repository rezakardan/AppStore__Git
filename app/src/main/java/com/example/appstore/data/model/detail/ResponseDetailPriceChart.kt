package com.example.appstore.data.model.detail


import com.google.gson.annotations.SerializedName

class ResponseDetailPriceChart : ArrayList<ResponseDetailPriceChart.ResponseDetailPriceChartItem>(){
    data class ResponseDetailPriceChartItem(
        @SerializedName("day")
        val day: String?, // 1402-12-07
        @SerializedName("price")
        val price: Int? // 6074100
    )
}