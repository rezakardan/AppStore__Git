package com.example.appstore.data.model.search


import com.google.gson.annotations.SerializedName

data class ResponseSearch(
    @SerializedName("products")
    val products: Products?
) {
    data class Products(
        @SerializedName("current_page")
        val currentPage: Int?, // 1
        @SerializedName("data")
        val `data`: List<Data>?,
        @SerializedName("first_page_url")
        val firstPageUrl: String?, // http:\/\/larashop.site\/api\/v1\/search?page=1
        @SerializedName("from")
        val from: Int?, // 1
        @SerializedName("last_page")
        val lastPage: Int?, // 1
        @SerializedName("last_page_url")
        val lastPageUrl: String?, // http:\/\/larashop.site\/api\/v1\/search?page=1
        @SerializedName("links")
        val links: List<Link?>?,
        @SerializedName("next_page_url")
        val nextPageUrl: Any?, // null
        @SerializedName("path")
        val path: String?, // http:\/\/larashop.site\/api\/v1\/search
        @SerializedName("per_page")
        val perPage: Int?, // 16
        @SerializedName("prev_page_url")
        val prevPageUrl: Any?, // null
        @SerializedName("to")
        val to: Int?, // 13
        @SerializedName("total")
        val total: Int? // 13
    ) {
        data class Data(
            @SerializedName("color_id")
            val colorId: List<String?>?,
            @SerializedName("colors")
            val colors: List<Color>?,
            @SerializedName("comments_avg_rate")
            val commentsAvgRate: String?, // 3.3333
            @SerializedName("comments_count")
            val commentsCount: Int?, // 3
            @SerializedName("created_at")
            val createdAt: String?, // 2021-12-30T13:05:49.000000Z
            @SerializedName("discount_rate")
            val discountRate: String?, // 10
            @SerializedName("discounted_price")
            val discountedPrice: Int?, // 0
            @SerializedName("end_time")
            val endTime: String?, // 2022-02-19T08:15:00.000000Z
            @SerializedName("final_price")
            val finalPrice: Int?, // 45600
            @SerializedName("id")
            val id: Int?, // 27
            @SerializedName("image")
            val image: String?, // \/storage\/cache\/400-0-0-width-iU4wWNM58WPlfMVXWPHDjUz5qwV0Aps6MTt3C8Kl.jpg
            @SerializedName("product_price")
            val productPrice: String?, // 6749000
            @SerializedName("quantity")
            val quantity: Int?, // 5
            @SerializedName("status")
            val status: String?, // special
            @SerializedName("title")
            val title: String? // \u06af\u0648\u0634\u06cc \u0645\u0648\u0628\u0627\u06cc\u0644 \u0634\u06cc\u0627\u0626\u0648\u0645\u06cc \u0645\u062f\u0644 POCO X3 Pro M2102J20SG
        ) {
            data class Color(
                @SerializedName("hex_code")
                val hexCode: String? // #000000
            )
        }

        data class Link(
            @SerializedName("active")
            val active: Boolean?, // false
            @SerializedName("label")
            val label: String?, // &laquo; \u0642\u0628\u0644\u06cc
            @SerializedName("url")
            val url: String? // http:\/\/larashop.site\/api\/v1\/search?page=1
        )
    }
}