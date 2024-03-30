package com.example.appstore.data.profile_address


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize



data class ResponseInsertOrUpdateAddress(
    @SerializedName("city_id")
    val cityId: String?, // 241
    @SerializedName("created_at")
    val createdAt: String?, // 2024-02-07T11:21:17.000000Z
    @SerializedName("floor")
    val floor: String?, // 2
    @SerializedName("id")
    val id: Int?, // 87
    @SerializedName("latitude")
    val latitude: String?, // 35.0
    @SerializedName("longitude")
    val longitude: String?, // 36.0
    @SerializedName("plate_number")
    val plateNumber: String?, // 40
    @SerializedName("postal_address")
    val postalAddress: String?, // آپاداد
    @SerializedName("postal_code")
    val postalCode: String?, // 1234567899
    @SerializedName("province_id")
    val provinceId: String?, // 5
    @SerializedName("receiver_cellphone")
    val receiverCellphone: String?, // 09390069834
    @SerializedName("receiver_firstname")
    val receiverFirstname: String?, // رضا
    @SerializedName("receiver_lastname")
    val receiverLastname: String?, // کاردان
    @SerializedName("updated_at")
    val updatedAt: String?, // 2024-02-07T11:21:17.000000Z
    @SerializedName("user_id")
    val userId: Int? // 252
)