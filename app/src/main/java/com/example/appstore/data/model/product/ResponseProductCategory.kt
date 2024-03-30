package com.example.appstore.data.model.product


import com.google.gson.annotations.SerializedName

data class ResponseProductCategory(
    @SerializedName("subCategory")
    val subCategory: SubCategory?
) {
    data class SubCategory(
        @SerializedName("id")
        val id: Int?, // 31
        @SerializedName("parent")
        val parent: Parent?,
        @SerializedName("parent_id")
        val parentId: Int?, // 9
        @SerializedName("products")
        val products: Products?,
        @SerializedName("products_count")
        val productsCount: Int?, // 3
        @SerializedName("slug")
        val slug: String?, // category-monitor
        @SerializedName("sub_categories")
        val subCategories: List<Any?>?,
        @SerializedName("title")
        val title: String? // \u0645\u0627\u0646\u06cc\u062a\u0648\u0631
    ) {
        data class Parent(
            @SerializedName("id")
            val id: Int?, // 9
            @SerializedName("parent")
            val parent: Parent?,
            @SerializedName("parent_id")
            val parentId: Int?, // 1
            @SerializedName("slug")
            val slug: String?, // category-computer-parts
            @SerializedName("title")
            val title: String? // \u06a9\u0627\u0645\u067e\u06cc\u0648\u062a\u0631 \u0648 \u062a\u062c\u0647\u06cc\u0632\u0627\u062a \u062c\u0627\u0646\u0628\u06cc
        ) {
            data class Parent(
                @SerializedName("id")
                val id: Int?, // 1
                @SerializedName("parent")
                val parent: Any?, // null
                @SerializedName("parent_id")
                val parentId: Int?, // 0
                @SerializedName("slug")
                val slug: String?, // electronic-devices
                @SerializedName("title")
                val title: String? // \u06a9\u0627\u0644\u0627\u06cc \u062f\u06cc\u062c\u06cc\u062a\u0627\u0644
            )
        }

        data class Products(
            @SerializedName("current_page")
            val currentPage: Int?, // 1
            @SerializedName("data")
            val `data`: List<Data>?,
            @SerializedName("first_page_url")
            val firstPageUrl: String?, // http:\/\/larashop.site\/api\/v1\/category\/pro\/category-monitor?page=1
            @SerializedName("from")
            val from: Int?, // 1
            @SerializedName("last_page")
            val lastPage: Int?, // 1
            @SerializedName("last_page_url")
            val lastPageUrl: String?, // http:\/\/larashop.site\/api\/v1\/category\/pro\/category-monitor?page=1
            @SerializedName("links")
            val links: List<Link?>?,
            @SerializedName("next_page_url")
            val nextPageUrl: Any?, // null
            @SerializedName("path")
            val path: String?, // http:\/\/larashop.site\/api\/v1\/category\/pro\/category-monitor
            @SerializedName("per_page")
            val perPage: Int?, // 16
            @SerializedName("prev_page_url")
            val prevPageUrl: Any?, // null
            @SerializedName("to")
            val to: Int?, // 3
            @SerializedName("total")
            val total: Int? // 3
        ) {
            data class Data(
                @SerializedName("color_id")
                val colorId: List<String?>?,
                @SerializedName("colors")
                val colors: List<Color>?,
                @SerializedName("comments_avg_rate")
                val commentsAvgRate: Any?, // null
                @SerializedName("comments_count")
                val commentsCount: Int?, // 0
                @SerializedName("created_at")
                val createdAt: String?, // 2022-01-01T15:03:42.000000Z
                @SerializedName("discount_rate")
                val discountRate: String?, // 20
                @SerializedName("discounted_price")
                val discountedPrice: Int?, // 0
                @SerializedName("end_time")
                val endTime: Any?, // null
                @SerializedName("final_price")
                val finalPrice: Int?, // 16800000
                @SerializedName("id")
                val id: Int?, // 61
                @SerializedName("image")
                val image: String?, // \/storage\/cache\/400-0-0-width-Hih4RT1HhDYypxFgbnLfIFA2ykGd7KgYORX4IxUm.jpg
                @SerializedName("product_price")
                val productPrice: String?, // 21000000
                @SerializedName("quantity")
                val quantity: Int?, // 12
                @SerializedName("status")
                val status: String?, // discount
                @SerializedName("title")
                val title: String?, // \u0645\u0627\u0646\u06cc\u062a\u0648\u0631 \u0627\u06cc\u0633\u0648\u0633 \u0645\u062f\u0644 XG32VC \u0633\u0627\u06cc\u0632 31.5 \u0627\u06cc\u0646\u0686
                @SerializedName("title_en")
                val titleEn: String? // monitor asus VG27WQ 27 inch
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
                val url: String? // http:\/\/larashop.site\/api\/v1\/category\/pro\/category-monitor?page=1
            )
        }
    }
}