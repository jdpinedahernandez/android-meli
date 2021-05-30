package com.juanpineda.meli.ui.modules.home.search.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
import com.juanpineda.meli.MeliApp
import com.juanpineda.meli.databinding.FragmentSearchBinding
import com.juanpineda.meli.ui.common.getViewModel
import com.juanpineda.meli.ui.common.hideKeyboard
import com.juanpineda.meli.ui.main.adapters.BannerAdapter
import com.juanpineda.meli.ui.main.adapters.CategoriesAdapter
import com.juanpineda.meli.ui.main.adapters.SearchingProductsAdapter
import com.juanpineda.meli.ui.modules.home.products.model.ProductData
import com.juanpineda.meli.ui.modules.home.products.model.ProductSearchType
import com.juanpineda.meli.ui.modules.home.products.model.ProductSearchType.*
import com.juanpineda.meli.ui.modules.home.search.SearchFragmentComponent
import com.juanpineda.meli.ui.modules.home.search.SearchFragmentModule
import com.juanpineda.meli.ui.modules.home.search.dialog.SearchDialogFragment
import com.juanpineda.meli.ui.modules.home.search.model.BannerFactory

class SearchFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentSearchBinding
    private lateinit var component: SearchFragmentComponent
    private val viewModel by lazy { getViewModel { component.searchViewModel } }
    private val searchingProductsAdapter by lazy {
        SearchingProductsAdapter {
            goToProducts(CATEGORY, it.id, it.name)
        }.apply {
            stateRestorationPolicy = PREVENT_WHEN_EMPTY
        }
    }
    private val bannerAdapter by lazy {
        BannerAdapter(BannerFactory().getList()) {
            goToProducts(CATEGORY, it.id, it.name)
        }.apply {
            stateRestorationPolicy = PREVENT_WHEN_EMPTY
        }
    }
    private val categoriesAdapter by lazy {
        CategoriesAdapter { goToProducts(CATEGORY, it.id, it.name) }.apply {
            stateRestorationPolicy = PREVENT_WHEN_EMPTY
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component = (activity?.application as MeliApp).component.plus(SearchFragmentModule())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        binding.viewModel = this@SearchFragment.viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        navController = findNavController()
        initViews()
        addListeners()
        return binding.root
    }

    private fun initViews() {
        binding.recyclerViewSearching.adapter = searchingProductsAdapter
        binding.recyclerViewCategories.adapter = categoriesAdapter
        binding.recyclerViewBanners.adapter = bannerAdapter
    }

    private fun addListeners() {
        binding.editTextFindProduct.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searching(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                goToProducts(PRODUCT, query)
                return true
            }
        })
        binding.floatingActionButtonSearch.setOnClickListener { showSearchFragment() }
        binding.viewError.buttonRetry.setOnClickListener { viewModel.getCategories() }
    }

    private fun goToProducts(
        searchType: ProductSearchType,
        search: String,
        searchTitle: String = search
    ) {
        binding.editTextFindProduct.hideKeyboard()
        binding.recyclerViewSearching.visibility = GONE
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToProductsFragment(
                ProductData(search, searchType, searchTitle)
            )
        )
    }

    private fun showSearchFragment() = activity?.let {
        SearchDialogFragment.Builder()
            .setOnFavoriteClickListener { goToProducts(FAVORITE, "favoritos") }
            .setOnViewedProductsClickListener { goToProducts(VIEWED, "vistos") }
            .create()
            .show(it.supportFragmentManager, "TAG")
    }
}