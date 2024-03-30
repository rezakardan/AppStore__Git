package com.example.appstore.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appstore.data.model.search.FilterModel
import com.example.appstore.data.model.search.ResponseSearch
import com.example.appstore.data.repository.SearchFilterRepository
import com.example.appstore.data.repository.SearchRepository
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class SearchViewModel@Inject constructor(private val repository:SearchRepository,private val filterRepository: SearchFilterRepository):ViewModel() {



    val searchLiveData=MutableLiveData<NetworkRequest<ResponseSearch>>()

    fun search(q:String,sort:String)=viewModelScope.launch {

        searchLiveData.value=NetworkRequest.Loading()

        val response=repository.getSearch(q, sort)

        searchLiveData.value=NetworkResponse(response).generateResponse()





    }



    val filterSearchLiveData=MutableLiveData<MutableList<FilterModel>>()

    fun filterSearch()=viewModelScope.launch{

        filterSearchLiveData.value=filterRepository.fillFilterData()





    }




    val sendSearchFilterLiveData=MutableLiveData<String>()

    fun sendSearchFilter(item:String){

        sendSearchFilterLiveData.value=item




    }



}