package com.juanpineda.usecases

import com.juanpineda.data.repository.CategoriesRepository
import com.juanpineda.data.server.result.ResultHandler
import com.juanpineda.domain.Product

class GetProductsByCategory(private val categoriesRepository: CategoriesRepository) {
    suspend fun invoke(query: String): ResultHandler<List<Product>> =
        categoriesRepository.getProductsByCategory(query)
}