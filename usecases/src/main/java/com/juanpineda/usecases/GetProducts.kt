package com.juanpineda.usecases

import com.juanpineda.data.repository.ProductsRepository
import com.juanpineda.domain.Product
import com.juanpineda.result.ResultHandler

class GetProducts(private val productsRepository: ProductsRepository) {
    suspend fun invoke(): List<Product> = productsRepository.getProducts()
    suspend fun byCategory(query: String): ResultHandler<List<Product>> = productsRepository.getProductsByCategory(query)
    suspend fun byName(query: String): ResultHandler<List<Product>> = productsRepository.getProductsByName(query)
}