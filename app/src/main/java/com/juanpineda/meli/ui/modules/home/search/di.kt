package com.juanpineda.meli.ui.modules.home.search

import com.juanpineda.data.repository.ProductsRepository
import com.juanpineda.meli.ui.modules.home.search.viewmodel.SearchViewModel
import com.juanpineda.usecases.GetFavoriteProducts
import com.juanpineda.usecases.GetPredictiveCategory
import com.juanpineda.usecases.GetProducts
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class SearchFragmentModule {

    @Provides
    fun searchViewModelProvider(
        getProducts: GetProducts,
        getPredictiveCategory: GetPredictiveCategory,
        getFavoriteProducts: GetFavoriteProducts
    ) = SearchViewModel(getProducts, getPredictiveCategory, getFavoriteProducts)

    @Provides
    fun getProductsProvider(productsRepository: ProductsRepository) =
        GetProducts(productsRepository)

    @Provides
    fun getPredictiveCategoryProvider(productsRepository: ProductsRepository) =
        GetPredictiveCategory(productsRepository)

    @Provides
    fun getFavoriteProductsProvider(productsRepository: ProductsRepository) =
        GetFavoriteProducts(productsRepository)
}

@Subcomponent(modules = [(SearchFragmentModule::class)])
interface SearchFragmentComponent {
    val searchViewModel: SearchViewModel
}