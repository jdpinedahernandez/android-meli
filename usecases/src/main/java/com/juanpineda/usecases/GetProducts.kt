package com.juanpineda.usecases

import com.juanpineda.data.repository.ProductsRepository
import com.juanpineda.domain.Product
import com.juanpineda.data.server.result.ResultHandler

class GetProducts(private val productsRepository: ProductsRepository) {
    suspend fun invoke(): List<Product> = productsRepository.getProducts()
}