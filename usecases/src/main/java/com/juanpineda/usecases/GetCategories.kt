package com.juanpineda.usecases

import com.juanpineda.data.repository.CategoriesRepository
import com.juanpineda.data.server.result.ResultHandler
import com.juanpineda.domain.Category

class GetCategories(private val categoriesRepository: CategoriesRepository) {
    suspend fun invoke(): ResultHandler<List<Category>> = categoriesRepository.getCategories()
}