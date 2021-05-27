package com.juanpineda.usecases

import com.juanpineda.data.repository.ProductsRepository
import com.juanpineda.domain.Product

class GetFavoriteProducts(private val productsRepository: ProductsRepository) {
    suspend fun invoke(): List<Product> = productsRepository.getProducts().filter { it.favorite }
}