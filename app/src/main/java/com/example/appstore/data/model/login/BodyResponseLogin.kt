package com.example.appstore.data.model.login


import com.google.gson.annotations.SerializedName

data class BodyResponseLogin(
    @SerializedName("login")
    var login: String?=null, //
    @SerializedName("hash_code")
    var hashCode: String?=null, //
    @SerializedName("code")
    var code: Int?=null, //

)