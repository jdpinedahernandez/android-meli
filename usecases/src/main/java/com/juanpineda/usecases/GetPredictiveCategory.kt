package com.juanpineda.usecases

import com.juanpineda.data.repository.ProductsRepository
import com.juanpineda.domain.Category
import com.juanpineda.result.ResultHandler

class GetPredictiveCategory(private val productsRepository: ProductsRepository) {
    suspend fun invoke(query: String): ResultHandler<List<Category>> = productsRepository.getPredictiveCategory(query)
}