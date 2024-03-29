package com.juanpineda.meli.ui.modules.home.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.juanpineda.domain.Category
import com.juanpineda.meli.databinding.ViewBannerBinding
import com.juanpineda.meli.ui.modules.home.search.model.BannerEntity

class BannerAdapter(
    private val banners: List<BannerEntity>,
    private val listener: (Category) -> Unit
) :
    RecyclerView.Adapter<BannersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BannersViewHolder(ViewBannerBinding.inflate(inflater))
    }

    override fun getItemCount(): Int = banners.size

    override fun onBindViewHolder(holder: BannersViewHolder, position: Int) {
        val banner = banners[position]
        holder.bind(banner, listener)
    }
}