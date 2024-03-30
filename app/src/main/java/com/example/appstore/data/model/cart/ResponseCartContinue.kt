package com.example.appstore.data.model.cart


import com.google.gson.annotations.SerializedName

data class ResponseCartContinue(
    @SerializedName("delivery_price")
    val deliveryPrice: Int?, // 0
    @SerializedName("final_price")
    val finalPrice: Int?, // 53660000
    @SerializedName("id")
    val id: Int?, // 265
    @SerializedName("items_discount")
    val itemsDiscount: String?, // 1970000
    @SerializedName("items_price")
    val itemsPrice: String?, // 53660000
    @SerializedName("order_items")
    val orderItems: List<OrderItem?>?,
    @SerializedName("quantity")
    val quantity: String?, // 4
    @SerializedName("status")
    val status: String?, // add-to-cart
    @SerializedName("updated_at")
    val updatedAt: String?, // 2024-03-07T08:48:23.000000Z
    @SerializedName("user_id")
    val userId: String? // 252
) {
    data class OrderItem(
        @SerializedName("approved")
        val approved: String?, // 1
        @SerializedName("color_hex_code")
        val colorHexCode: String?, // #999999
        @SerializedName("color_title")
        val colorTitle: String?, // خاکستری
        @SerializedName("discounted_price")
        val discountedPrice: String?, // 0
        @SerializedName("final_price")
        val finalPrice: String?, // 15800000
        @SerializedName("id")
        val id: Int?, // 586
        @SerializedName("order_id")
        val orderId: String?, // 265
        @SerializedName("price")
        val price: String?, // 7900000
        @SerializedName("product_guarantee")
        val productGuarantee: String?, // گارانتی اصالت و سلامت فیزیکی کالا
        @SerializedName("product_id")
        val productId: String?, // 47
        @SerializedName("product_image")
        val productImage: String?, // /storage/cache/600-0-0-width-ewE1bQJKBMwNMfP8eNqqpj4UU00wmhOv9zP6xSoL.jpg
        @SerializedName("product_quantity")
        val productQuantity: String?, // 6
        @SerializedName("product_title")
        val productTitle: String?, // کفش کوهنوردی مردانه سالیوا مدل THE ALPINE FIT کد EM-5400
        @SerializedName("quantity")
        val quantity: String? // 2
    )
}