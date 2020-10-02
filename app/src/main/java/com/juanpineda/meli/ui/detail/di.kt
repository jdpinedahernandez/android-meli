package com.juanpineda.meli.ui.detail

import com.juanpineda.data.repository.ProductsRepository
import com.juanpineda.meli.ui.detail.viewmodel.DetailViewModel
import com.juanpineda.usecases.FindProductById
import com.juanpineda.usecases.ToggleProductFavorite
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class DetailActivityModule(private val productId: String) {

    @Provides
    fun detailViewModelProvider(
            findProductById: FindProductById,
            toggleProductFavorite: ToggleProductFavorite
    ): DetailViewModel {
        return DetailViewModel(productId, findProductById, toggleProductFavorite)
    }

    @Provides
    fun findProductByIdProvider(productsRepository: ProductsRepository) = FindProductById(productsRepository)

    @Provides
    fun toggleProductFavoriteProvider(productsRepository: ProductsRepository) = ToggleProductFavorite(productsRepository)
}

@Subcomponent(modules = [(DetailActivityModule::class)])
interface DetailActivityComponent {
    val detaiViewModel: DetailViewModel
}