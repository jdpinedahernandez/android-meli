package com.juanpineda.meli.ui.modules.home.search.viewmodel

import com.juanpineda.data.server.result.onError
import com.juanpineda.data.server.result.onSuccess
import com.juanpineda.domain.Category
import com.juanpineda.meli.ui.modules.home.common.ScopedViewModel
import com.juanpineda.meli.ui.modules.home.common.SingleLiveEvent
import com.juanpineda.meli.ui.modules.home.common.asLiveData
import com.juanpineda.usecases.GetCategories
import com.juanpineda.usecases.GetPredictiveCategory
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getPredictiveCategory: GetPredictiveCategory,
    private val getCategories: GetCategories
) : ScopedViewModel() {
    private val _model = SingleLiveEvent<UiModel>()
    val model get() = _model.asLiveData()
    private lateinit var searchJob: Job

    class LoadPredictiveCategory(val categories: List<Category>) : UiModel.FeatureModel()
    class LoadCategories(val categories: List<Category>) : UiModel.FeatureModel()

    init {
        initScope()
        getCategories()
    }

    fun searchingPredictiveCategory(query: String) {
        if (::searchJob.isInitialized) searchJob.cancel()
        if (query.isNotEmpty())
            searchJob = launch {
                getPredictiveCategory.invoke(query)
                    .onSuccess { _model.postValue(LoadPredictiveCategory(it)) }
            }
        else _model.postValue(UiModel.EmptyState)
    }

    fun getCategories() =
        launch {
            _model.value = UiModel.Loading
            getCategories.invoke()
                .onSuccess { _model.value = LoadCategories(it) }
                .onError { _model.value = UiModel.ErrorState }
        }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}