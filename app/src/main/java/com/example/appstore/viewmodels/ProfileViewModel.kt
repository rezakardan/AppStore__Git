package com.example.appstore.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appstore.data.model.profile.BodyUpdateProfile
import com.example.appstore.data.model.profile.ResponseProfile
import com.example.appstore.data.repository.ProfileRepository
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: ProfileRepository) :
    ViewModel() {

    val profileLiveData = MutableLiveData<NetworkRequest<ResponseProfile>>()

    fun callProfileApi() = viewModelScope.launch {


        profileLiveData.value = NetworkRequest.Loading()

        val response = repository.getProfile()

        profileLiveData.value = NetworkResponse(response).generateResponse()


    }


    val uploadAvatarLiveData = MutableLiveData<NetworkRequest<Unit>>()

    fun uploadAvatar(body: RequestBody) = viewModelScope.launch {

        uploadAvatarLiveData.value = NetworkRequest.Loading()

        val response = repository.postUploadAvatar(body)

        uploadAvatarLiveData.value = NetworkResponse(response).generateResponse()

    }


    val updateProfileLiveData = MutableLiveData<NetworkRequest<ResponseProfile>>()

    fun updateProfile(body: BodyUpdateProfile) = viewModelScope.launch {


        updateProfileLiveData.value = NetworkRequest.Loading()


        val response = repository.updateProfile(body)

        updateProfileLiveData.value = NetworkResponse(response).generateResponse()


    }
}