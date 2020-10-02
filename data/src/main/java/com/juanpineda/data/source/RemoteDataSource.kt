package com.juanpineda.data.source

import com.juanpineda.domain.Category
import com.juanpineda.domain.Product
import com.juanpineda.result.ResultHandler

interface RemoteDataSource {
    suspend fun getPredictiveCategory(query: String): ResultHandler<List<Category>>
    suspend fun getProductsByCategory(query: String): ResultHandler<List<Product>>
    suspend fun getProductsByName(query: String): ResultHandler<List<Product>>
    suspend fun getProductDetail(itemId: String): ResultHandler<Product>
}