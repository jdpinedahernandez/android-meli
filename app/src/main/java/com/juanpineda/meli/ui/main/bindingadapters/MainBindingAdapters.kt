package com.juanpineda.meli.ui.main.bindingadapters

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.juanpineda.meli.R
import com.juanpineda.meli.ui.main.viewmodel.MainViewModel.UiModel
import com.juanpineda.meli.ui.main.viewmodel.MainViewModel.UiModel.*

@BindingAdapter("uiLoading")
fun View.uiLoading(uiState: UiModel?) {
    visibility = if (uiState is Loading) VISIBLE
    else GONE
}

@BindingAdapter("uiSearching")
fun View.uiSearching(uiState: UiModel?) {
    visibility = if (uiState is Searching) VISIBLE
    else GONE
}

@BindingAdapter("uiContent")
fun View.uiContent(uiState: UiModel?) {
    visibility = when (uiState) {
        is LoadLocalContent, is LoadRemoteContent, is Navigation -> VISIBLE
        else -> GONE
    }
}

@BindingAdapter("uiNoData")
fun TextView.uiNoData(uiState: UiModel?) {
    visibility = when (uiState) {
        EmptyState -> {
            text = context.getText(R.string.product_main_empty_products)
            VISIBLE
        }
        ErrorState -> {
            text = context.getText(R.string.product_main_error)
            VISIBLE
        }
        else -> GONE
    }
}