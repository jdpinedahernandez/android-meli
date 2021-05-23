package com.juanpineda.meli.ui.detail.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.juanpineda.domain.Product
import com.juanpineda.meli.R
import com.juanpineda.meli.databinding.ActivityDetailBinding
import com.juanpineda.meli.ui.common.app
import com.juanpineda.meli.ui.common.getViewModel
import com.juanpineda.meli.ui.common.loadContent
import com.juanpineda.meli.ui.detail.DetailActivityComponent
import com.juanpineda.meli.ui.detail.DetailActivityModule
import com.juanpineda.meli.ui.detail.viewmodel.DetailViewModel
import com.juanpineda.meli.ui.detail.viewmodel.DetailViewModel.UiModel.LoadDetailContent
import com.juanpineda.meli.ui.detail.viewmodel.DetailViewModel.UiModel.LoadFavoriteContent

class DetailActivity : AppCompatActivity() {

    companion object {
        const val PRODUCT = "DetailActivity:product"
    }

    private lateinit var component: DetailActivityComponent
    private val viewModel by lazy { getViewModel { component.detaiViewModel } }
    private lateinit var binding: ActivityDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        component = app.component.plus(DetailActivityModule(intent.getStringExtra(PRODUCT) ?: ""))
        setContentView(binding.root)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: DetailViewModel.UiModel) = when (model) {
        is LoadDetailContent -> loadContentView(model.product)
        is LoadFavoriteContent -> loadContentView(model.product)
    }

    private fun loadContentView(product: Product) = with(binding) {
        productDetailSummary.text = product.title
        viewPagerProducts.loadContent(product.pictures)
        productDetailInfo.setProduct(product)
        productDetailFavorite.setImageDrawable(getDrawable(if (product.favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off))
    }
}