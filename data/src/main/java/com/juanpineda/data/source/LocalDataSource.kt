package com.juanpineda.data.source

import com.juanpineda.domain.Product
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun isEmpty(): Boolean
    suspend fun saveProducts(products: List<Product>)
    suspend fun saveProduct(product: Product)
    suspend fun getProducts(): List<Product>
    suspend fun getProductsByTitle(title: String): List<Product>
    suspend fun findById(id: String): Product
    suspend fun isProductIsExist(id: String): Boolean
    suspend fun update(product: Product)
    fun getFavoriteProducts(): Flow<List<Product>>
}