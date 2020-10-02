package com.juanpineda.meli.ui.main.adapters

import androidx.recyclerview.widget.RecyclerView
import com.juanpineda.domain.Category
import com.juanpineda.meli.databinding.ViewSearchingProductBinding

class SearchingProductsViewHolder(var binding: ViewSearchingProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
    fun bind(category: Category, listener: (Category) -> Unit) {
        binding.category = category
        itemView.setOnClickListener { listener(category) }
    }
}