package com.juanpineda.data.server

import com.juanpineda.data.source.RemoteDataSource
import com.juanpineda.data.toDomainCategory
import com.juanpineda.data.toDomainProduct
import com.juanpineda.result.ErrorResponse
import com.juanpineda.result.Failure
import com.juanpineda.result.SuccessResponse

class MeliDbDataSource(private val meliDb: MeliDb) : RemoteDataSource {

    override suspend fun getPredictiveCategory(query: String) =
            try {
                SuccessResponse(meliDb.service.getPredictiveCategoryAsync(query).map { it.toDomainCategory() })
            } catch (e: Exception) {
                ErrorResponse(Failure.analyzeException(e))
            }

    override suspend fun getProductsByCategory(query: String) =
            try {
                SuccessResponse(meliDb.service.getProductsByCategoryAsync(query).results.map { it.toDomainProduct() })
            } catch (e: Exception) {
                ErrorResponse(Failure.analyzeException(e))
            }

    override suspend fun getProductsByName(query: String) =
            try {
                SuccessResponse(meliDb.service.getProductsByNameAsync(query).results.map { it.toDomainProduct() })
            } catch (e: Exception) {
                ErrorResponse(Failure.analyzeException(e))
            }

    override suspend fun getProductDetail(itemId: String) =
            try {
                SuccessResponse(meliDb.service.getProductDetailAsync(itemId).let { it.toDomainProduct() })
            } catch (e: Exception) {
                ErrorResponse(Failure.analyzeException(e))
            }

}