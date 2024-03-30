package com.example.appstore.data.model.shipping


import com.google.gson.annotations.SerializedName

data class ResponseSetAddress(
    @SerializedName("address")
    val address: Address?,
    @SerializedName("message")
    val message: String? // آدرس دریافت بروزرسانی شد.
) {
    data class Address(
        @SerializedName("id")
        val id: Int?, // 90
        @SerializedName("postal_address")
        val postalAddress: String?, // میدان کاج
        @SerializedName("receiver_cellphone")
        val receiverCellphone: String?, // 09369854317
        @SerializedName("receiver_firstname")
        val receiverFirstname: String?, // علی
        @SerializedName("receiver_lastname")
        val receiverLastname: String? // محمدی
    )
}