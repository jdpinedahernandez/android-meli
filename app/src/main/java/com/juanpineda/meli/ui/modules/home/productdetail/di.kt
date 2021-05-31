package com.juanpineda.meli.ui.modules.home.productdetail

import com.juanpineda.data.repository.ProductsRepository
import com.juanpineda.meli.ui.modules.home.productdetail.viewmodel.ProductDetailViewModel
import com.juanpineda.usecases.FindProductById
import com.juanpineda.usecases.ToggleProductFavorite
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class ProductDetailFragmentModule(private val productId: String) {

    @Provides
    fun detailViewModelProvider(
            findProductById: FindProductById,
            toggleProductFavorite: ToggleProductFavorite
    ): ProductDetailViewModel {
        return ProductDetailViewModel(productId, findProductById, toggleProductFavorite)
    }

    @Provides
    fun findProductByIdProvider(productsRepository: ProductsRepository) = FindProductById(productsRepository)

    @Provides
    fun toggleProductFavoriteProvider(productsRepository: ProductsRepository) = ToggleProductFavorite(productsRepository)
}

@Subcomponent(modules = [(ProductDetailFragmentModule::class)])
interface ProductDetailFragmentComponent {
    val productDetailViewModel: ProductDetailViewModel
}