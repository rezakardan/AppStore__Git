package com.example.appstore.data.model.login


import com.google.gson.annotations.SerializedName

data class ResponseVerify(
    @SerializedName("access_token")
    val accessToken: String?, // eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiNGQwMWI0NWViYTliYmYzZGVlYmVkMzlmNTY1YjNkMDY4N2UxOTFjNWE5NjE3YjZkNTg4ZDJiMzNkMmIwYjBmYjk0NWM4NjQwYjg3ZGViYTciLCJpYXQiOjE3MDM3NjQzMTcuMTA2MzU1LCJuYmYiOjE3MDM3NjQzMTcuMTA2MzU3LCJleHAiOjE3MTY4OTcxMTcuMDg0Mjc1LCJzdWIiOiIyNTIiLCJzY29wZXMiOltdfQ.sgNMD_-1sLlMew4ZoMHmidNeu8bjaybUeczABtx1kTqP6_6U9kS1MNvhhpwTDiHVFku7IbBEiyu1N17vfm0oYz0wlDZ3-v6pQFZOULpx1wkkNdlhY_H5eUEu_CuyoI4a5taJRV_vMTB_F0cgZkLeRIPOmyV5wZUIfinX8fhee6ubHyXmSxsQmg1V3Vp8BcaumNSejacqIgeBcd1t1r94A5ZSU7RqFqHG2fMIri1Mr7Qnjyl_wpHOvH4w3ju1-BtLYVDWzS-sVsULJGaqdiHfrnEGoPOVCu2wkWPUIbRv3SF_XOOWVW4LgENhf2gsjEuH1Spy1SJJ5xAJ4Azb-keZfGsmft0VtzoQgFFD2VZ8uA749DMBfXytRYsaWOvqeT7sJy924kqXwgQKPb8878JhXoWqxOm1pSRtPMdvyT8Vjvsr9xQ33aWM3Wqy7H71rg7TPU_A8u5cOw16DvIVZ0wOhxL05eMKsxzHMdhtCUaH3cuI0Upbif1KvSUMnyFgKTmpcSrVWQsDsCG5xj9iL62Y-cTzDLccBsOL9DqFaEDJrAK45UcvtQMtyInFUdC86IiscdPOm5WWh6uNMDrXb_meS8Z6YvRPjp94Yze2BsSfr0zsgr3nkMxP48XmZUPmJFcNZ56Dp06S_HThRYKjg65uRZfXxALdu7t_2QIUQM6g06U
    @SerializedName("expires_in")
    val expiresIn: Int?, // 13132800
    @SerializedName("refresh_token")
    val refreshToken: String?, // def50200fcb8bc161091386f80a1287ad7d9f2da9d85c738ce0429658c5c0203d773e6568f34204ed4705ad3a30a828d3a26505cf64a8087bdc0eb3ad7742a53ed1727016069862325198da77dfa2357be04ab36c65e4e63b7925251ee503dcd2cd791c7c012602565ccb2ea55cbd465aac3d4f5425c68104e3e9c1b26f1a3116e84f3ca2357d17ed1c82866c10d4d12789cd82a9af6c575f061298373da74a0366c813476382f927b1a0904f994a0a97ca3ccaf051ed60822c1990ab0145792bc6502d448dfc8b7104b797bb64de02f0a680a9f86ed8370da439a54ca322f39ea522e565fe14df337912ec889c0e91e07123886b0672ae7e57d49ee5d2ed8d17ce50dbcc1eea6c4bd268aa62da5798f945d81fa3422213444dd71b083c8d2c08163445e3ea5fe6c10ad614fb715d8f2e1f2b6f157b6a8765b2b26f03addb0b155ace748cef24fffc34f6973f32a0df42144fae220d11184bf9dedb2f2d9e972bb4a21
    @SerializedName("token_type")
    val tokenType: String?, // Bearer
    @SerializedName("user_detail")
    val userDetail: UserDetail?
) {
    data class UserDetail(
        @SerializedName("avatar")
        val avatar: Any?, // null
        @SerializedName("bank_account")
        val bankAccount: Any?, // null
        @SerializedName("birth_date")
        val birthDate: Any?, // null
        @SerializedName("cellphone")
        val cellphone: String?, // 09390069834
        @SerializedName("email")
        val email: Any?, // null
        @SerializedName("email_verified_at")
        val emailVerifiedAt: Any?, // null
        @SerializedName("firstname")
        val firstname: Any?, // null
        @SerializedName("id")
        val id: Int?, // 252
        @SerializedName("id_number")
        val idNumber: Any?, // null
        @SerializedName("job_title")
        val jobTitle: Any?, // null
        @SerializedName("lastname")
        val lastname: Any?, // null
        @SerializedName("type")
        val type: String?, // customer
        @SerializedName("updated")
        val updated: String?, // الان
        @SerializedName("wallet")
        val wallet: Int? // 0
    )
}