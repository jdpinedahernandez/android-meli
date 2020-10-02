package com.juanpineda.meli.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.juanpineda.domain.Product
import com.juanpineda.meli.databinding.ViewProductBinding
import com.juanpineda.meli.ui.common.basicDiffUtil

class ContentProductsAdapter(private val listener: (Product) -> Unit) :
        RecyclerView.Adapter<ContentProductsViewHolder>() {

    var products: List<Product> by basicDiffUtil(
            emptyList(),
            areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentProductsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ContentProductsViewHolder(ViewProductBinding.inflate(inflater))
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ContentProductsViewHolder, position: Int) {
        holder.bind(products[position], listener)
    }

}