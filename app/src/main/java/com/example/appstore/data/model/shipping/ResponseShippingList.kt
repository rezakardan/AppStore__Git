package com.example.appstore.data.model.shipping


import com.google.gson.annotations.SerializedName

data class ResponseShippingList(
    @SerializedName("addresses")
    val addresses: List<Addresse>?,
    @SerializedName("order")
    val order: Order?,
    @SerializedName("status")
    val status: Boolean? // true
) {
    data class Addresse(
        @SerializedName("id")
        val id: Int?, // 90
        @SerializedName("postal_address")
        val postalAddress: String?, // میدان کاج
        @SerializedName("receiver_cellphone")
        val receiverCellphone: String?, // 09369854317
        @SerializedName("receiver_firstname")
        val receiverFirstname: String?, // علی
        @SerializedName("receiver_lastname")
        val receiverLastname: String? // محمدی
    )

    data class Order(
        @SerializedName("address")
        val address: Address?,
        @SerializedName("address_id")
        val addressId: Any?, // null
        @SerializedName("delivery_price")
        val deliveryPrice: String?, // 0
        @SerializedName("discounted_rate")
        val discountedRate: Int?, // 5
        @SerializedName("final_price")
        val finalPrice: String?, // 37461500
        @SerializedName("id")
        val id: Int?, // 265
        @SerializedName("items_discount")
        val itemsDiscount: String?, // 2028500
        @SerializedName("items_price")
        val itemsPrice: String?, // 37461500
        @SerializedName("order_items")
        val orderItems: List<OrderItem>,
        @SerializedName("quantity")
        val quantity: String? // 2
    ) {
        class Address

        data class OrderItem(
            @SerializedName("color_hex_code")
            val colorHexCode: String?, // #999999
            @SerializedName("color_title")
            val colorTitle: String?, // خاکستری
            @SerializedName("discounted_price")
            val discountedPrice: String?, // 1970000
            @SerializedName("order_id")
            val orderId: String?, // 265
            @SerializedName("product_id")
            val productId: String?, // 38
            @SerializedName("product_image")
            val productImage: String?, // /storage/cache/300-0-0-width-1vNiVqJscM7EGtqrD7TpZfmxO15g3ph8LD12EROr.jpg
            @SerializedName("product_title")
            val productTitle: String?, // لپ تاپ 13 اینچی اپل مدل MacBook Pro MYD82 2020
            @SerializedName("quantity")
            val quantity: String? // 1
        )
    }
}