package com.example.appstore.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appstore.data.model.category.FilterCategoriesModel
import com.example.appstore.data.model.product.ResponseProductCategory
import com.example.appstore.data.model.search.FilterModel
import com.example.appstore.data.repository.CategoryProductRepository
import com.example.appstore.data.repository.SearchFilterRepository
import com.example.appstore.utils.network.NetworkRequest
import com.example.appstore.utils.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoryProductViewModel @Inject constructor(
    private val repository: CategoryProductRepository,
    private val filterRepository: SearchFilterRepository
) :
    ViewModel() {

    val productLiveData = MutableLiveData<NetworkRequest<ResponseProductCategory>>()


    fun categoryProduct(
        slug: String,
        search: String? = null,
        sort: String? = null,
        minPrice: String? = null,
        maxPrice: String? = null,
        selectedBrands: String? = null,
        selectedColors: String? = null,
        page: String? = "1",
        onlyAvailable: Boolean? = null
    ) = viewModelScope.launch {

        productLiveData.value = NetworkRequest.Loading()


        val response = repository.categoryProduct(
            slug, search, sort, minPrice, maxPrice,
            selectedBrands, selectedColors, page, onlyAvailable
        )
        productLiveData.value = NetworkResponse(response).generateResponse()


    }


    val filterLiveData=MutableLiveData<MutableList<FilterModel>>()
fun getFilter()=viewModelScope.launch {


    filterLiveData.value=filterRepository.fillFilterData()


}


    val sendFilterLiveData=MutableLiveData<FilterCategoriesModel>()

    fun sendFilter(  search: String? = null,
                     sort: String? = null, minPrice: String? = null,
                       maxPrice: String? = null, onlyAvailable: Boolean? = null){


sendFilterLiveData.value= FilterCategoriesModel(search, sort, minPrice, maxPrice, onlyAvailable)




    }




}