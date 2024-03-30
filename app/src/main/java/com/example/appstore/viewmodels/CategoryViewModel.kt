package com.example.appstore.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appstore.data.model.category.ResponseCategory
import com.example.appstore.data.repository.CategoryRepository
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class CategoryViewModel@Inject constructor(private val repository:CategoryRepository):ViewModel() {


    val categoryLiveData=MutableLiveData<NetworkRequest<ResponseCategory>>()


    fun callCategoryApi()=viewModelScope.launch {

        categoryLiveData.value=NetworkRequest.Loading()


        val response=repository.getCategory()


        categoryLiveData.value=NetworkResponse(response).generateResponse()







    }



}