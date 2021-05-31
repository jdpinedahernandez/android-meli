package com.juanpineda.meli.ui.modules.home.search.view.bindingadapter

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.juanpineda.meli.ui.modules.home.common.ScopedViewModel.UiModel
import com.juanpineda.meli.ui.modules.home.search.adapters.CategoriesAdapter
import com.juanpineda.meli.ui.modules.home.search.adapters.SearchingProductsAdapter
import com.juanpineda.meli.ui.modules.home.search.viewmodel.SearchViewModel

@BindingAdapter("uiLoading")
fun View.uiLoading(uiState: UiModel?) {
    visibility = if (uiState is UiModel.Loading) VISIBLE
    else GONE
}

@BindingAdapter("uiShowContent")
fun View.uiShowContent(uiState: UiModel?) {
    visibility = when (uiState) {
        UiModel.Loading, UiModel.ErrorState, is SearchViewModel.LoadPredictiveCategory -> GONE
        else -> VISIBLE
    }
}

@BindingAdapter("uiErrorState")
fun View.uiErrorState(uiState: UiModel?) {
    visibility = when (uiState) {
        UiModel.ErrorState -> VISIBLE
        else -> GONE
    }
}

@BindingAdapter("uiLoadPredictiveCategory")
fun RecyclerView.uiLoadPredictiveCategory(uiState: UiModel?) {
    when (uiState) {
        is SearchViewModel.LoadPredictiveCategory -> {
            visibility = VISIBLE
            (adapter as SearchingProductsAdapter).categories =
                uiState.categories.toMutableList()
        }
        else -> visibility = GONE
    }
}

@BindingAdapter("uiLoadCategories")
fun RecyclerView.uiLoadCategories(uiState: UiModel?) {
    when (uiState) {
        is SearchViewModel.LoadCategories -> {
            visibility = VISIBLE
            (adapter as CategoriesAdapter).categories =
                uiState.categories.toMutableList()
        }
        UiModel.EmptyState -> visibility = VISIBLE
        else -> visibility = GONE
    }
}