package com.juanpineda.meli.ui.modules.home.products.model

import java.io.Serializable

data class ProductData(
    val search: String,
    val productSearchType: ProductSearchType,
    val searchTitle: String
) : Serializable