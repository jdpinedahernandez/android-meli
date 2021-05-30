package com.juanpineda.meli.ui.modules.home.products.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
import com.juanpineda.domain.Product
import com.juanpineda.meli.MeliApp
import com.juanpineda.meli.databinding.FragmentProductsBinding
import com.juanpineda.meli.ui.common.ScopedViewModel
import com.juanpineda.meli.ui.common.getViewModel
import com.juanpineda.meli.ui.main.adapters.ContentProductsAdapter
import com.juanpineda.meli.ui.modules.home.products.ProductsFragmentComponent
import com.juanpineda.meli.ui.modules.home.products.ProductsFragmentModule
import com.juanpineda.meli.ui.modules.home.products.viewmodel.ProductsViewModel

class ProductsFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentProductsBinding
    private val contentProductsAdapter by lazy {
        ContentProductsAdapter(::goToDetailProduct).apply {
            stateRestorationPolicy = PREVENT_WHEN_EMPTY
        }
    }
    private lateinit var component: ProductsFragmentComponent
    private val viewModel by lazy { getViewModel { component.productsViewModel } }
    private val args: ProductsFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component = (activity?.application as MeliApp).component.plus(
            ProductsFragmentModule(
                args.productSearchType,
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        binding.lifecycleOwner = this@ProductsFragment
        initViews()
    }

    private fun initViews() {
        binding.textViewRecentProducts.text = args.productSearchType.searchTitle
        binding.recyclerViewProducts.adapter = contentProductsAdapter
        viewModel.model.observe(viewLifecycleOwner, Observer(::updateUi))
    }

    private fun updateUi(model: ScopedViewModel.UiModel) = when (model) {
        is ProductsViewModel.LoadProducts -> showProducts(model.products)
        is ScopedViewModel.UiModel.Loading -> showLoader()
        is ScopedViewModel.UiModel.ErrorState -> showError()
        is ScopedViewModel.UiModel.EmptyState -> showEmpty()
        else -> {
        }
    }

    private fun showProducts(products: List<Product>) = with(binding) {
        contentProductsAdapter.products = products
        progressBarProducts.visibility = GONE
        groupContentProducts.visibility = VISIBLE
        viewInformation.visibility = GONE
    }

    private fun showLoader() = with(binding) {
        progressBarProducts.visibility = VISIBLE
        groupContentProducts.visibility = GONE
        viewInformation.visibility = GONE
    }

    private fun showError() = with(binding) {
        progressBarProducts.visibility = GONE
        groupContentProducts.visibility = GONE
        viewInformation.visibility = VISIBLE
        viewInformation.showErrorView(onErrorListener = viewModel::search)
    }

    private fun showEmpty() = with(binding) {
        progressBarProducts.visibility = GONE
        groupContentProducts.visibility = GONE
        viewInformation.visibility = VISIBLE
        viewInformation.showEmptyView(onEmptyListener = ::gotBack)
    }

    private fun gotBack() {
        findNavController().popBackStack()
    }

    private fun goToDetailProduct(product: Product) {
        findNavController().navigate(
            ProductsFragmentDirections.actionProductsFragmentToProductDetailFragment(
                product.id
            )
        )
    }
}