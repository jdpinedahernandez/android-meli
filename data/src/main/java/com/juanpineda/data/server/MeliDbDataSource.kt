package com.juanpineda.data.server

import com.juanpineda.data.server.result.ResultHandler
import com.juanpineda.data.server.result.resultHandlerOf
import com.juanpineda.data.source.RemoteDataSource
import com.juanpineda.data.toDomainCategory
import com.juanpineda.data.toDomainProduct
import com.juanpineda.domain.Category

class MeliDbDataSource(private val meliDb: MeliDb) : RemoteDataSource {

    override suspend fun getPredictiveCategory(query: String) =
        resultHandlerOf {
            meliDb.service.getPredictiveCategoryAsync(query).map { it.toDomainCategory() }
        }

    override suspend fun getCategories(): ResultHandler<List<Category>> =
        resultHandlerOf { meliDb.service.getCategoriesAsync().map { it.toDomainCategory() } }

    override suspend fun getProductsByCategory(query: String) =
        resultHandlerOf {
            meliDb.service.getProductsByCategoryAsync(query).results.map { it.toDomainProduct() }
        }

    override suspend fun getProductsByName(query: String) =
        resultHandlerOf {
            meliDb.service.getProductsByNameAsync(query).results.map { it.toDomainProduct() }
        }

    override suspend fun getProductDetail(itemId: String) =
        resultHandlerOf {
            meliDb.service.getProductDetailAsync(itemId).let { it.toDomainProduct() }
        }
}