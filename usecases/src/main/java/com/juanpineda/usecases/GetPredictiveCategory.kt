package com.juanpineda.usecases

import com.juanpineda.data.repository.CategoriesRepository
import com.juanpineda.data.repository.ProductsRepository
import com.juanpineda.domain.Category
import com.juanpineda.data.server.result.ResultHandler

class GetPredictiveCategory(private val categoriesRepository: CategoriesRepository) {
    suspend fun invoke(query: String): ResultHandler<List<Category>> = categoriesRepository.getPredictiveCategory(query)
}