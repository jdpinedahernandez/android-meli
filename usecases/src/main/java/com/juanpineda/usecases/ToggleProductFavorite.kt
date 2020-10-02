package com.juanpineda.usecases

import com.juanpineda.data.repository.ProductsRepository

class ToggleProductFavorite(private val productsRepository: ProductsRepository) {
    suspend fun invoke(id: String) = with(productsRepository.findById(id)) {
        copy(favorite = !favorite).also { productsRepository.update(it) }
    }
}