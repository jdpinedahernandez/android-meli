package com.juanpineda.meli.ui.modules.home.products.viewmodel

import com.juanpineda.data.server.result.onError
import com.juanpineda.data.server.result.onSuccess
import com.juanpineda.domain.Product
import com.juanpineda.meli.ui.common.ScopedViewModel
import com.juanpineda.meli.ui.common.SingleLiveEvent
import com.juanpineda.meli.ui.common.asLiveData
import com.juanpineda.meli.ui.modules.home.products.model.ProductData
import com.juanpineda.meli.ui.modules.home.products.model.ProductSearchType.*
import com.juanpineda.usecases.GetFavoriteProducts
import com.juanpineda.usecases.GetProducts
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val productData: ProductData,
    private val getProducts: GetProducts,
    private val getFavoriteProducts: GetFavoriteProducts
) : ScopedViewModel() {
    private val _model = SingleLiveEvent<UiModel>()
    val model get() = _model.asLiveData()
    private lateinit var searchJob: Job

    class LoadProducts(val products: List<Product>, val title: String) : UiModel.FeatureModel()

    init {
        initScope()
        search()
    }

    fun search() {
        when (productData.productSearchType) {
            CATEGORY -> getProductsByCategory(productData.search)
            PRODUCT -> getProductsByName(productData.search)
            FAVORITE -> getFavoriteProducts()
            VIEWED -> getLocalProducts()
        }
    }

    private fun getProductsByCategory(categoryId: String) {
        if (::searchJob.isInitialized) searchJob.cancel()
        searchJob = launch {
            _model.value = UiModel.Loading
            getProducts.byCategory(categoryId)
                .onSuccess { _model.value = LoadProducts(it, "category.name") }
                .onError { _model.value = UiModel.ErrorState }
        }
    }

    private fun getProductsByName(query: String) {
        launch {
            _model.value = UiModel.Loading
            getProducts.byName(query)
                .onSuccess { _model.value = LoadProducts((it), query) }
                .onError { _model.value = UiModel.ErrorState }
        }
    }

    private fun getLocalProducts() {
        launch {
            _model.value = UiModel.Loading
            _model.value = with(getProducts.invoke()) {
                if (this.isEmpty()) UiModel.EmptyState
                else LoadProducts(this, "Vistos")
            }
        }
    }

    private fun getFavoriteProducts() = launch {
        getFavoriteProducts.invoke().collect {
            if (it.isNotEmpty())
                _model.value = LoadProducts(it, "Favoritos")
            else _model.value = UiModel.EmptyState
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}