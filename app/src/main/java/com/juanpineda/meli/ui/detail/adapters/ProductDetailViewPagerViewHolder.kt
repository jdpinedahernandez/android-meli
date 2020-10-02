package com.juanpineda.meli.ui.detail.adapters

import androidx.recyclerview.widget.RecyclerView
import com.juanpineda.meli.databinding.ViewPicturesBinding
import com.juanpineda.meli.ui.detail.bindingadapters.loadUrl

class ProductDetailViewPagerViewHolder(var binding: ViewPicturesBinding) :
        RecyclerView.ViewHolder(binding.root) {
    fun bind(picture: String) {
        binding.imageViewPicture.loadUrl(picture)
    }
}