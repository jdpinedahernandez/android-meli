package com.juanpineda.meli.ui.modules.home.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.juanpineda.domain.Category
import com.juanpineda.meli.databinding.ViewSearchingProductBinding

class SearchingProductsAdapter(private val listener: (Category) -> Unit) :
        RecyclerView.Adapter<SearchingProductsViewHolder>() {

    var categories = mutableListOf<Category>()
        set(value) {
            categories.clear()
            categories.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchingProductsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchingProductsViewHolder(ViewSearchingProductBinding.inflate(inflater))
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: SearchingProductsViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category, listener)
    }
}