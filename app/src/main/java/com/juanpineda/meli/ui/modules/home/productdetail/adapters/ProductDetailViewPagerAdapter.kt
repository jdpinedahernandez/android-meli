package com.juanpineda.meli.ui.modules.home.productdetail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.juanpineda.meli.databinding.ViewPicturesBinding

class ProductDetailViewPagerAdapter(private val pictures: List<String>) :
        RecyclerView.Adapter<ProductDetailViewPagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDetailViewPagerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProductDetailViewPagerViewHolder(ViewPicturesBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ProductDetailViewPagerViewHolder, position: Int) {
        holder.bind(pictures[position])
    }

    override fun getItemCount(): Int = pictures.size
}