package com.juanpineda.data.repository

import com.juanpineda.data.server.result.ResultHandler
import com.juanpineda.data.source.RemoteDataSource
import com.juanpineda.domain.Category
import com.juanpineda.domain.Product

class CategoriesRepository(
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun getPredictiveCategory(query: String): ResultHandler<List<Category>> = remoteDataSource.getPredictiveCategory(query)

    suspend fun getCategories(): ResultHandler<List<Category>> = remoteDataSource.getCategories()

    suspend fun getProductsByCategory(query: String): ResultHandler<List<Product>> = remoteDataSource.getProductsByCategory(query)
}