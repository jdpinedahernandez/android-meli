package com.juanpineda.meli.ui.detail.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.juanpineda.domain.Product
import com.juanpineda.meli.R
import com.juanpineda.meli.databinding.ActivityDetailBinding
import com.juanpineda.meli.ui.common.loadContent
import com.juanpineda.meli.ui.detail.viewmodel.DetailViewModel
import com.juanpineda.meli.ui.detail.viewmodel.DetailViewModel.UiModel.LoadDetailContent
import com.juanpineda.meli.ui.detail.viewmodel.DetailViewModel.UiModel.LoadFavoriteContent
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.parameter.parametersOf

class DetailActivity : AppCompatActivity() {

    companion object {
        const val PRODUCT = "DetailActivity:product"
    }

    private val viewModel: DetailViewModel by lifecycleScope.viewModel(this) {
        parametersOf(intent.getStringExtra(PRODUCT) ?: "")
    }
    private lateinit var binding: ActivityDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        setContentView(binding.root)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: DetailViewModel.UiModel) = when (model) {
        is LoadDetailContent -> loadContentView(model.product)
        is LoadFavoriteContent -> loadDetailFavoriteView(model.product)
    }

    private fun loadContentView(product: Product) = with(product) {
        binding.productDetailSummary.text = product.title
        binding.viewPagerProducts.loadContent(pictures)
        loadDetailFavoriteView(this)
    }

    private fun loadDetailFavoriteView(product: Product) = with(product) {
        binding.productDetailInfo.setProduct(this)
        binding.productDetailFavorite.setImageDrawable(getDrawable(if (favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off))
    }
}