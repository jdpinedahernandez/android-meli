package com.juanpineda.meli.ui.modules.home.products.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.juanpineda.domain.Product
import com.juanpineda.meli.databinding.FragmentProductsBinding
import com.juanpineda.meli.ui.main.adapters.ContentProductsAdapter
import com.juanpineda.meli.ui.modules.home.search.view.SearchFragmentDirections

class ProductsFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentProductsBinding
    private lateinit var contentProductsAdapter: ContentProductsAdapter
    private val args: ProductsFragmentArgs by navArgs()

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
        contentProductsAdapter = ContentProductsAdapter(::goToDetailProduct)
        binding.recyclerViewProducts.adapter = contentProductsAdapter
        contentProductsAdapter.products = args.products.toMutableList()
    }

    private fun goToDetailProduct(product: Product) {
        findNavController().navigate(
            ProductsFragmentDirections.actionProductsFragmentToProductDetailFragment(
                product.id
            )
        )
    }
}