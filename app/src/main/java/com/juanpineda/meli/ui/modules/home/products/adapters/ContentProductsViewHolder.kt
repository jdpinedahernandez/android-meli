package com.juanpineda.meli.ui.modules.home.products.adapters

import androidx.recyclerview.widget.RecyclerView
import com.juanpineda.domain.Product
import com.juanpineda.meli.databinding.ViewProductBinding

class ContentProductsViewHolder(var binding: ViewProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product, listener: (Product) -> Unit) {
        binding.product = product
        itemView.setOnClickListener { listener(product) }
    }
}