package com.example.appstore.data.model.profile


import com.google.gson.annotations.SerializedName

data class ResponseProfile(
    @SerializedName("avatar")
    val avatar: Any?, // null
    @SerializedName("birth_date")
    val birthDate: String?, // 1402-10-08T20:34:16.000000Z
    @SerializedName("cellphone")
    val cellphone: String?, // 09390069834
    @SerializedName("email")
    val email: String?, // null
    @SerializedName("firstname")
    val firstname: String?, // null
    @SerializedName("id")
    val id: Int?, // 252
    @SerializedName("idNumber")
    val idNumber: String?, // null
    @SerializedName("job")
    val job: String?, // null
    @SerializedName("lastname")
    val lastname: String?, // null
    @SerializedName("register_date")
    val registerDate: String?, // 01 ، دی ، 02
    @SerializedName("wallet")
    val wallet: String? // 0
)