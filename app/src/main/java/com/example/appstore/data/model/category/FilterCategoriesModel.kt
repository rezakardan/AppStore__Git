package com.example.appstore.data.model.category

data class FilterCategoriesModel( val search: String? = null,
                                 val sort: String? = null,val minPrice: String? = null,
                               val  maxPrice: String? = null,val onlyAvailable: Boolean? = null)
