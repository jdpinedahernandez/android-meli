package com.juanpineda.meli.ui.modules.home.products

import com.juanpineda.data.repository.CategoriesRepository
import com.juanpineda.data.repository.ProductsRepository
import com.juanpineda.meli.ui.modules.home.products.model.ProductData
import com.juanpineda.meli.ui.modules.home.products.viewmodel.ProductsViewModel
import com.juanpineda.usecases.GetFavoriteProducts
import com.juanpineda.usecases.GetProductByName
import com.juanpineda.usecases.GetProducts
import com.juanpineda.usecases.GetProductsByCategory
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class ProductsFragmentModule(private val productData: ProductData) {

    @Provides
    fun productsViewModelProvider(
        getProducts: GetProducts,
        getProductByName: GetProductByName,
        getFavoriteProducts: GetFavoriteProducts,
        getProductsByCategory: GetProductsByCategory
    ) = ProductsViewModel(
        productData,
        getProducts,
        getProductByName,
        getFavoriteProducts,
        getProductsByCategory
    )

    @Provides
    fun getProductsProvider(productsRepository: ProductsRepository) =
        GetProducts(productsRepository)

    @Provides
    fun getProductByNameProvider(productsRepository: ProductsRepository) =
        GetProductByName(productsRepository)

    @Provides
    fun getFavoriteProductsProvider(productsRepository: ProductsRepository) =
        GetFavoriteProducts(productsRepository)

    @Provides
    fun getProductsByCategoryProvider(categoriesRepository: CategoriesRepository) =
        GetProductsByCategory(categoriesRepository)

}

@Subcomponent(modules = [(ProductsFragmentModule::class)])
interface ProductsFragmentComponent {
    val productsViewModel: ProductsViewModel
}