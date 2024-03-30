package com.example.appstore.data.model.profile_order


import com.google.gson.annotations.SerializedName

data class ResponseProfileOrders(
    @SerializedName("current_page")
    val currentPage: Int?, // 1
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("first_page_url")
    val firstPageUrl: String?, // http://larashop.site/api/v1/auth/orders?page=1
    @SerializedName("from")
    val from: Int?, // 1
    @SerializedName("last_page")
    val lastPage: Int?, // 1
    @SerializedName("last_page_url")
    val lastPageUrl: String?, // http://larashop.site/api/v1/auth/orders?page=1
    @SerializedName("links")
    val links: List<Link?>?,
    @SerializedName("next_page_url")
    val nextPageUrl: Any?, // null
    @SerializedName("path")
    val path: String?, // http://larashop.site/api/v1/auth/orders
    @SerializedName("per_page")
    val perPage: Int?, // 10
    @SerializedName("prev_page_url")
    val prevPageUrl: Any?, // null
    @SerializedName("to")
    val to: Int?, // 1
    @SerializedName("total")
    val total: Int? // 1
) {
    data class Data(
        @SerializedName("final_price")
        val finalPrice: String?, // 131500
        @SerializedName("id")
        val id: Int?, // 1
        @SerializedName("order_items")
        val orderItems: List<OrderItem>?,
        @SerializedName("status")
        val status: String?, // payment-cancel
        @SerializedName("updated_at")
        val updatedAt: String? // 2023-01-05T08:00:27.000000Z
    ) {
        data class OrderItem(
            @SerializedName("extras")
            val extras: Extras?,
            @SerializedName("order_id")
            val orderId: Int?, // 1
            @SerializedName("product_id")
            val productId: Int?, // 56
            @SerializedName("product_title")
            val productTitle: String? // دفتر برنامه ریزی مستر راد طرح جدید کاکتوس مدل پالت کد 1587
        ) {
            data class Extras(
                @SerializedName("color")
                val color: Int?, // 3
                @SerializedName("discounted_price")
                val discountedPrice: String?, // 58500
                @SerializedName("guarantee")
                val guarantee: Any?, // null
                @SerializedName("id")
                val id: Int?, // 56
                @SerializedName("image")
                val image: String?, // /storage/cache/150-0-0-width-TuiZ3fwo6SEH3gh70fqAJMxBzBpTfBA7UkDMH32K.jpg
                @SerializedName("price")
                val price: String?, // 90000
                @SerializedName("quantity")
                val quantity: Int?, // 1
                @SerializedName("title")
                val title: String? // دفتر برنامه ریزی مستر راد طرح جدید کاکتوس مدل پالت کد 1587
            )
        }
    }

    data class Link(
        @SerializedName("active")
        val active: Boolean?, // false
        @SerializedName("label")
        val label: String?, // &laquo; قبلی
        @SerializedName("url")
        val url: String? // http://larashop.site/api/v1/auth/orders?page=1
    )
}