package com.juanpineda.meli.ui.main

import com.juanpineda.data.repository.ProductsRepository
import com.juanpineda.meli.ui.main.viewmodel.MainViewModel
import com.juanpineda.usecases.GetPredictiveCategory
import com.juanpineda.usecases.GetProducts
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class MainActivityModule {

    @Provides
    fun mainViewModelProvider(getProducts: GetProducts, getPredictiveCategory: GetPredictiveCategory) = MainViewModel(getProducts, getPredictiveCategory)

    @Provides
    fun getProductsProvider(productsRepository: ProductsRepository) =
            GetProducts(productsRepository)

    @Provides
    fun getPredictiveCategoryProvider(productsRepository: ProductsRepository) =
            GetPredictiveCategory(productsRepository)
}

@Subcomponent(modules = [(MainActivityModule::class)])
interface MainActivityComponent {
    val mainViewModel: MainViewModel
}