package com.juanpineda.meli.ui.modules.home.search

import com.juanpineda.data.repository.CategoriesRepository
import com.juanpineda.meli.ui.modules.home.search.viewmodel.SearchViewModel
import com.juanpineda.usecases.GetCategories
import com.juanpineda.usecases.GetPredictiveCategory
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class SearchFragmentModule() {

    @Provides
    fun searchViewModelProvider(
        getPredictiveCategory: GetPredictiveCategory,
        getCategories: GetCategories
    ) = SearchViewModel(getPredictiveCategory, getCategories)

    @Provides
    fun getPredictiveCategoryProvider(categoriesRepository: CategoriesRepository) =
        GetPredictiveCategory(categoriesRepository)

    @Provides
    fun getCategoriesProvider(categoriesRepository: CategoriesRepository) =
        GetCategories(categoriesRepository)
}

@Subcomponent(modules = [(SearchFragmentModule::class)])
interface SearchFragmentComponent {
    val searchViewModel: SearchViewModel
}