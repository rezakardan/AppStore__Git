package com.example.appstore.data.repository

import com.example.appstore.data.model.login.BodyResponseLogin
import com.example.appstore.data.server.ApiService
import retrofit2.http.Body
import javax.inject.Inject

class LoginRepository @Inject constructor(private val api: ApiService) {


    suspend fun postLogin(body: BodyResponseLogin) = api.postLogin(body)

    suspend fun postVerify(body: BodyResponseLogin) = api.postVerifyLogin(body)

}