package com.juanpineda.data.server.category

import com.google.gson.annotations.SerializedName

data class MeliDbPredictiveCategoryResult(
        @SerializedName("category_id") val categoryId: String,
        @SerializedName("category_name") val categoryName: String
)

