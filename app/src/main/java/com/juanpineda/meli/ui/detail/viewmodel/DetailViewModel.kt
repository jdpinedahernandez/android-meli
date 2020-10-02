package com.juanpineda.meli.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.juanpineda.domain.Product
import com.juanpineda.meli.ui.common.ScopedViewModel
import com.juanpineda.usecases.FindProductById
import com.juanpineda.usecases.ToggleProductFavorite
import kotlinx.coroutines.launch

class DetailViewModel(
        private val itemId: String,
        private val findProductById: FindProductById,
        private val toggleProductFavorite: ToggleProductFavorite
) :
        ScopedViewModel() {

    sealed class UiModel {
        class LoadDetailContent(val product: Product) : UiModel()
        class LoadFavoriteContent(val product: Product) : UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) findProduct()
            return _model
        }

    private fun findProduct() = launch {
        _model.value = UiModel.LoadDetailContent(findProductById.invoke(itemId))
    }

    fun onFavoriteClicked() = launch {
        _model.value = UiModel.LoadFavoriteContent(toggleProductFavorite.invoke(itemId))
    }
}