package com.juanpineda.data.repository

import com.juanpineda.data.source.LocalDataSource
import com.juanpineda.data.source.RemoteDataSource
import com.juanpineda.domain.Category
import com.juanpineda.domain.Product
import com.juanpineda.data.server.result.ResultHandler
import com.juanpineda.data.server.result.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class ProductsRepository(
        private val localDataSource: LocalDataSource,
        private val remoteDataSource: RemoteDataSource
) {

    suspend fun getProductsByName(query: String): ResultHandler<List<Product>> = remoteDataSource.getProductsByName(query)

    suspend fun getProducts(): List<Product> = if (localDataSource.isEmpty().not()) localDataSource.getProducts() else emptyList()

    fun getFavoriteProducts(): Flow<List<Product>> = localDataSource.getFavoriteProducts()

    suspend fun findById(id: String): Product {
        return if (localDataSource.isProductIsExist(id)) {
            localDataSource.findById(id)
        } else {
            remoteDataSource.getProductDetail(id).onSuccess { localDataSource.saveProduct(it) }
            localDataSource.findById(id)
        }
    }

    suspend fun update(product: Product) = localDataSource.update(product)
}