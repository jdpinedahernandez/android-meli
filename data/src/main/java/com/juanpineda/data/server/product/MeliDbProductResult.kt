package com.juanpineda.data.server.product

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class MeliDbProductResult(
    val query: String,
    val results: List<Product>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

@Parcelize
data class Product(
        val id: String,
        val title: String,
        val price: Long,
        val thumbnail: String,
        @SerializedName("category_id") val categoryId: String
) : Parcelable