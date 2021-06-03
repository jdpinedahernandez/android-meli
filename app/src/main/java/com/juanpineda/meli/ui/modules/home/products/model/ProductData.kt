package com.juanpineda.meli.ui.modules.home.products.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductData(
    val search: String,
    val productSearchType: ProductSearchType,
    val searchTitle: String
) : Parcelable