package com.example.appstore.data.model.cart


import com.google.gson.annotations.SerializedName

data class ResponseUpdateCart(
    @SerializedName("discounted_price")
    val discountedPrice: String?, // 0
    @SerializedName("final_price")
    val finalPrice: Int?, // 75300000
    @SerializedName("id")
    val id: Int?, // 4
    @SerializedName("order_id")
    val orderId: Int?, // 3
    @SerializedName("price")
    val price: String?, // 25100000
    @SerializedName("product_id")
    val productId: Int?, // 31
    @SerializedName("quantity")
    val quantity: Int?, // 3
    @SerializedName("updated_at")
    val updatedAt: String?, // 2023-01-29T16:14:46.000000Z
    @SerializedName("user_id")
    val userId: Int? // 6
)