package com.juanpineda.meli.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.juanpineda.domain.Category
import com.juanpineda.domain.Product
import com.juanpineda.meli.ui.common.ScopedViewModel
import com.juanpineda.result.onError
import com.juanpineda.result.onSuccess
import com.juanpineda.usecases.GetPredictiveCategory
import com.juanpineda.usecases.GetProducts
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(private val getProducts: GetProducts, private val getPredictiveCategory: GetPredictiveCategory) : ScopedViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) getLocalProducts()
            return _model
        }
    private lateinit var searchJob: Job

    sealed class UiModel {
        class Searching(val categories: List<Category>) : UiModel()
        object Loading : UiModel()
        object EmptyState : UiModel()
        object ErrorState : UiModel()
        class LoadRemoteContent(val products: List<Product>, val title: String) : UiModel()
        class LoadLocalContent(val products: List<Product>) : UiModel()
        class Navigation(val product: Product) : UiModel()
    }

    init {
        initScope()
    }

    fun searching(query: String) {
        if (query.isNotEmpty())
            launch {
                getPredictiveCategory.invoke(query).onSuccess { _model.value = UiModel.Searching(it) }
            }
    }

    fun endSearchByCategory(category: Category) {
        if (::searchJob.isInitialized) searchJob.cancel()
        searchJob = launch {
            _model.value = UiModel.Loading
            getProducts.byCategory(category.id)
                    .onSuccess { _model.value = UiModel.LoadRemoteContent(it, category.name) }
                    .onError { _model.value = UiModel.ErrorState }
        }
    }

    fun endSearchByName(query: String) {
        launch {
            _model.value = UiModel.Loading
            getProducts.byName(query)
                    .onSuccess { _model.value = UiModel.LoadRemoteContent((it), query) }
                    .onError { _model.value = UiModel.ErrorState }
        }
    }

    fun getLocalProducts() {
        launch {
            _model.value = UiModel.Loading
            _model.value = with(getProducts.invoke()) {
                if (this.isEmpty()) UiModel.EmptyState
                else UiModel.LoadLocalContent(this)
            }
        }
    }

    fun onProductClicked(product: Product) {
        _model.value = UiModel.Navigation(product)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}