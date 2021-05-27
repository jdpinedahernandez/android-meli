package com.juanpineda.meli.ui.modules.home.search.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.juanpineda.domain.Product
import com.juanpineda.meli.MeliApp
import com.juanpineda.meli.databinding.FragmentSearchBinding
import com.juanpineda.meli.ui.common.getViewModel
import com.juanpineda.meli.ui.main.adapters.BannerAdapter
import com.juanpineda.meli.ui.main.adapters.CategoriesAdapter
import com.juanpineda.meli.ui.main.adapters.SearchingProductsAdapter
import com.juanpineda.meli.ui.modules.home.search.SearchFragmentComponent
import com.juanpineda.meli.ui.modules.home.search.SearchFragmentModule
import com.juanpineda.meli.ui.modules.home.search.dialog.SearchDialogFragment
import com.juanpineda.meli.ui.modules.home.search.model.BannerFactory
import com.juanpineda.meli.ui.modules.home.search.viewmodel.SearchViewModel
import com.juanpineda.meli.ui.modules.home.search.viewmodel.SearchViewModel.UiModel.*

class SearchFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentSearchBinding
    private lateinit var component: SearchFragmentComponent
    private val viewModel by lazy { getViewModel { component.searchViewModel } }
    private lateinit var searchingProductsAdapter: SearchingProductsAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var bannerAdapter: BannerAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component = (activity?.application as MeliApp).component.plus(SearchFragmentModule())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this@SearchFragment
        initViews()
        addListeners()
    }

    private fun initViews() {
        searchingProductsAdapter = SearchingProductsAdapter(viewModel::endSearchByCategory)
        binding.recyclerViewSearching.adapter = searchingProductsAdapter
        categoriesAdapter = CategoriesAdapter(viewModel::endSearchByCategory)
        binding.recyclerViewCategories.adapter = categoriesAdapter
        bannerAdapter = BannerAdapter(viewModel::endSearchByCategory)
        binding.recyclerViewBanners.adapter = bannerAdapter
        bannerAdapter.banners = BannerFactory().getList().toMutableList()
        viewModel.getCategories()
    }

    private fun addListeners() {
        viewModel.model.observe(viewLifecycleOwner, Observer(::updateUi))
        binding.editTextFindProduct.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searching(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.endSearchByName(query)
                return true
            }
        })
        binding.floatingActionButtonSearch.setOnClickListener { showSearchFragment() }
    }

    private fun updateUi(model: SearchViewModel.UiModel) = when (model) {
        is Searching -> searchingProductsAdapter.categories = model.categories.toMutableList()
        is LoadCategories -> categoriesAdapter.categories = model.categories.toMutableList()
        is LoadRemoteContent -> goToProducts(model.products)
        is LoadLocalContent -> goToProducts(model.products)
        else -> {
        }
    }

    private fun goToProducts(products: List<Product>) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToProductsFragment(
                products.toTypedArray()
            )
        )
    }

    private fun showSearchFragment() = activity?.let {
        SearchDialogFragment.Builder()
            .setOnFavoriteClickListener(viewModel::getFavoriteProducts)
            .setOnViewedProductsClickListener(viewModel::getLocalProducts)
            .create()
            .show(it.supportFragmentManager, "TAG")
    }
}