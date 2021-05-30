package com.juanpineda.usecases

import com.juanpineda.data.repository.ProductsRepository
import com.juanpineda.domain.Product
import kotlinx.coroutines.flow.Flow

class GetFavoriteProducts(private val productsRepository: ProductsRepository) {
    fun invoke(): Flow<List<Product>> = productsRepository.getFavoriteProducts()
}