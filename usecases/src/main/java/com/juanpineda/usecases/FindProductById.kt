package com.juanpineda.usecases

import com.juanpineda.data.repository.ProductsRepository
import com.juanpineda.domain.Product

class FindProductById(private val productsRepository: ProductsRepository) {
    suspend fun invoke(itemId: String): Product = productsRepository.findById(itemId)
}