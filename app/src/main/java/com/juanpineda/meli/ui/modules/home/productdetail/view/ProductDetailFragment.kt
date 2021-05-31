package com.juanpineda.meli.ui.modules.home.productdetail.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.juanpineda.domain.Product
import com.juanpineda.meli.MeliApp
import com.juanpineda.meli.R
import com.juanpineda.meli.databinding.ProductDetailBinding
import com.juanpineda.meli.ui.modules.home.common.getViewModel
import com.juanpineda.meli.ui.modules.home.common.loadContent
import com.juanpineda.meli.ui.modules.home.productdetail.ProductDetailFragmentComponent
import com.juanpineda.meli.ui.modules.home.productdetail.ProductDetailFragmentModule
import com.juanpineda.meli.ui.modules.home.productdetail.viewmodel.ProductDetailViewModel

class ProductDetailFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding: ProductDetailBinding
    private lateinit var component: ProductDetailFragmentComponent
    private val viewModel by lazy { getViewModel { component.productDetailViewModel } }
    private val args: ProductDetailFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component =
            (activity?.application as MeliApp).component.plus(ProductDetailFragmentModule(args.productId))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProductDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        viewModel.model.observe(viewLifecycleOwner, Observer(::updateUi))
        binding.productDetailFavorite.setOnClickListener { viewModel.onFavoriteClicked() }
    }

    private fun updateUi(model: ProductDetailViewModel.UiModel) = when (model) {
        is ProductDetailViewModel.UiModel.LoadDetailContent -> loadContentView(model.product)
        is ProductDetailViewModel.UiModel.LoadFavoriteContent -> updateFavoriteState(model.product)
    }

    private fun loadContentView(product: Product) = with(binding) {
        productDetailSummary.text = product.title
        viewPagerProducts.loadContent(product.pictures)
        productDetailInfo.setProduct(product)
        binding.productDetailFavorite.setImageDrawable(requireContext().getDrawable(if (product.favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off))
    }

    private fun updateFavoriteState(product: Product) {
        binding.productDetailFavorite.setImageDrawable(requireContext().getDrawable(if (product.favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off))
        if (product.favorite)
            Snackbar.make(
                binding.root, getString(R.string.product_detail_favorite),
                Snackbar.LENGTH_SHORT
            ).show()
    }
}