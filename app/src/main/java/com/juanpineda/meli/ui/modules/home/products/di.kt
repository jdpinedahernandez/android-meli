package com.juanpineda.meli.ui.modules.home.products

import com.juanpineda.data.repository.ProductsRepository
import com.juanpineda.meli.ui.modules.home.products.model.ProductData
import com.juanpineda.meli.ui.modules.home.products.viewmodel.ProductsViewModel
import com.juanpineda.usecases.GetFavoriteProducts
import com.juanpineda.usecases.GetProducts
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class ProductsFragmentModule(private val productData: ProductData) {

    @Provides
    fun productsViewModelProvider(
        getProducts: GetProducts,
        getFavoriteProducts: GetFavoriteProducts
    ) = ProductsViewModel(productData, getProducts, getFavoriteProducts)

    @Provides
    fun getProductsProvider(productsRepository: ProductsRepository) =
        GetProducts(productsRepository)

    @Provides
    fun getFavoriteProductsProvider(productsRepository: ProductsRepository) =
        GetFavoriteProducts(productsRepository)
}

@Subcomponent(modules = [(ProductsFragmentModule::class)])
interface ProductsFragmentComponent {
    val productsViewModel: ProductsViewModel
}