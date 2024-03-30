package com.example.appstore.data.model.cart


import com.google.gson.annotations.SerializedName

data class ResponseGetCartList(
    @SerializedName("delivery_price")
    val deliveryPrice: String?, // 0
    @SerializedName("discounted_rate")
    val discountedRate: Int?, // 4
    @SerializedName("final_price")
    val finalPrice: String?, // 61087500
    @SerializedName("id")
    val id: Int?, // 265
    @SerializedName("items_discount")
    val itemsDiscount: Int?, // 1970000
    @SerializedName("items_price")
    val itemsPrice: String?, // 53230000
    @SerializedName("order_items")
    val orderItems: List<OrderItem>?,
    @SerializedName("quantity")
    val quantity: String?, // 3
    @SerializedName("status")
    val status: String?, // add-to-cart
    @SerializedName("updated_at")
    val updatedAt: String?, // 2024-03-05T15:39:58.000000Z
    @SerializedName("user_id")
    val userId: String? // 252
) {
    data class OrderItem(
        @SerializedName("approved")
        val approved: String?, // 1
        @SerializedName("color_hex_code")
        val colorHexCode: String?, // #000000
        @SerializedName("color_title")
        val colorTitle: String?, // مشکی
        @SerializedName("discounted_price")
        val discountedPrice: String?, // 674900
        @SerializedName("final_price")
        val finalPrice: Int?, // 0
        @SerializedName("id")
        val id: Int?, // 585
        @SerializedName("order_id")
        val orderId: String?, // 265
        @SerializedName("price")
        val price: String?, // 6749000
        @SerializedName("product_guarantee")
        val productGuarantee: String?, // گارانتی ۱۸ ماهه هما تلکام
        @SerializedName("product_id")
        val productId: String?, // 27
        @SerializedName("product_image")
        val productImage: String?, // /storage/cache/600-0-0-width-iU4wWNM58WPlfMVXWPHDjUz5qwV0Aps6MTt3C8Kl.jpg
        @SerializedName("product_quantity")
        val productQuantity: String?, // 0
        @SerializedName("product_title")
        val productTitle: String?, // گوشی موبایل شیائومی مدل POCO X3 Pro M2102J20SG
        @SerializedName("quantity")
        val quantity: String?, // 0
        @SerializedName("updated_at")
        val updatedAt: String?, // 2024-03-05T15:39:58.000000Z
        @SerializedName("user_id")
        val userId: String? // 252
    )
}