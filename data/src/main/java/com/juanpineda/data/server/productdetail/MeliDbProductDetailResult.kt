package com.juanpineda.data.server.productdetail

import com.google.gson.annotations.SerializedName

data class MeliDbProductDetailResult(
    val id: String,
    val title: String,
    val price: Long,
    val thumbnail: String,
    @SerializedName("category_id") val categoryId: String,
    @SerializedName("available_quantity") val availableQuantity: Int,
    val pictures: List<Picture>,
    val status: String,
    val warranty: String,
    @SerializedName("currency_id") val currencyId: String
)

data class Picture(
        val id: String,
        val url: String,
        @SerializedName("secure_url") val secureUrl: String
)