package com.juanpineda.meli.ui.modules.home.search.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
import com.juanpineda.meli.MeliApp
import com.juanpineda.meli.R
import com.juanpineda.meli.databinding.FragmentSearchBinding
import com.juanpineda.meli.ui.modules.home.common.getViewModel
import com.juanpineda.meli.ui.modules.home.common.hideKeyboard
import com.juanpineda.meli.ui.modules.home.search.adapters.BannerAdapter
import com.juanpineda.meli.ui.modules.home.search.adapters.CategoriesAdapter
import com.juanpineda.meli.ui.modules.home.search.adapters.SearchingProductsAdapter
import com.juanpineda.meli.ui.modules.home.products.model.ProductData
import com.juanpineda.meli.ui.modules.home.products.model.ProductSearchType
import com.juanpineda.meli.ui.modules.home.products.model.ProductSearchType.*
import com.juanpineda.meli.ui.modules.home.search.SearchFragmentComponent
import com.juanpineda.meli.ui.modules.home.search.SearchFragmentModule
import com.juanpineda.meli.ui.modules.home.search.dialog.SearchDialogFragment
import com.juanpineda.meli.ui.modules.home.search.model.BannerFactory

class SearchFragment : Fragment() {
    companion object {
        const val TAG = "SearchFragment"
    }

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
        BannerAdapter(BannerFactory(requireContext()).getList()) {
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
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        addListeners()
    }

    private fun initViews() {
        binding.recyclerViewSearching.adapter = searchingProductsAdapter
        binding.recyclerViewCategories.adapter = categoriesAdapter
        binding.recyclerViewBanners.adapter = bannerAdapter
    }

    private fun addListeners() {
        binding.editTextFindProductOrCategory.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchingPredictiveCategory(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                goToProducts(PRODUCT, query)
                return true
            }
        })
        binding.floatingActionButtonSearch.setOnClickListener { showSearchFragment() }
        binding.viewInformation.showErrorView(viewModel::getCategories)
    }

    private fun goToProducts(
        searchType: ProductSearchType,
        search: String,
        searchTitle: String = search
    ) {
        binding.editTextFindProductOrCategory.hideKeyboard()
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToProductsFragment(
                ProductData(search, searchType, searchTitle)
            )
        )
    }

    private fun showSearchFragment() = activity?.let {
        SearchDialogFragment.Builder()
            .setOnFavoriteClickListener {
                goToProducts(
                    FAVORITE,
                    getString(R.string.search_favorite_title)
                )
            }
            .setOnViewedProductsClickListener {
                goToProducts(
                    VIEWED,
                    getString(R.string.search_viewed_title)
                )
            }
            .create()
            .show(it.supportFragmentManager, TAG)
    }
}