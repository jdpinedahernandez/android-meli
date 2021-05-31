package com.juanpineda.usecases

import com.juanpineda.data.repository.ProductsRepository
import com.juanpineda.domain.Product
import com.juanpineda.data.server.result.ResultHandler

class GetProductByName(private val productsRepository: ProductsRepository) {
    suspend fun invoke(query: String): ResultHandler<List<Product>> = productsRepository.getProductsByName(query)
}