package com.example.appstore.data.repository

import com.example.appstore.data.model.profile.BodyUpdateProfile
import com.example.appstore.data.server.ApiService
import okhttp3.RequestBody
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val api: ApiService) {


    suspend fun getProfile() = api.getProfile()

    suspend fun postUploadAvatar(body: RequestBody) = api.postUploadAvatar(body)
    suspend fun updateProfile(body: BodyUpdateProfile) = api.updateProfile(body)

}