package com.juanpineda.meli.ui.modules.home.search.adapters

import androidx.recyclerview.widget.RecyclerView
import com.juanpineda.domain.Category
import com.juanpineda.meli.databinding.ViewBannerBinding
import com.juanpineda.meli.ui.modules.home.search.model.BannerEntity

class BannersViewHolder(var binding: ViewBannerBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(banner: BannerEntity, listener: (Category) -> Unit) {
        binding.banner = banner
        binding.imageViewBanner.setImageResource(banner.image)
        itemView.setOnClickListener { listener(banner.category) }
    }
}