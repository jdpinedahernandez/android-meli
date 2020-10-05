package com.juanpineda.meli.ui.main.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.juanpineda.domain.Product
import com.juanpineda.meli.R
import com.juanpineda.meli.databinding.ActivityMainBinding
import com.juanpineda.meli.ui.common.startActivityForResult
import com.juanpineda.meli.ui.detail.view.DetailActivity
import com.juanpineda.meli.ui.detail.view.DetailActivity.Companion.PRODUCT
import com.juanpineda.meli.ui.main.adapters.ContentProductsAdapter
import com.juanpineda.meli.ui.main.adapters.SearchingProductsAdapter
import com.juanpineda.meli.ui.main.viewmodel.MainViewModel
import com.juanpineda.meli.ui.main.viewmodel.MainViewModel.UiModel
import com.juanpineda.meli.ui.main.viewmodel.MainViewModel.UiModel.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel


class MainActivity : FragmentActivity() {

    companion object {
        const val CODE = 1
    }

    private val viewModel: MainViewModel by lifecycleScope.viewModel(this)
    private lateinit var contentProductsAdapter: ContentProductsAdapter
    private lateinit var searchingProductsAdapter: SearchingProductsAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        initViews()
        addListeners()
    }

    private fun initViews() {
        contentProductsAdapter = ContentProductsAdapter(viewModel::onProductClicked)
        searchingProductsAdapter = SearchingProductsAdapter(viewModel::endSearchByCategory)
        binding.recycler.adapter = contentProductsAdapter
        binding.recyclerViewSearching.adapter = searchingProductsAdapter
    }

    private fun addListeners() {
        viewModel.model.observe(this, Observer(::updateUi))
        binding.editTextFindProduct.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searching(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.endSearchByName(query)
                return true
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.getLocalProducts()
    }

    private fun updateUi(model: UiModel) = when (model) {
        is Searching -> searchingProductsAdapter.categories = model.categories.toMutableList()
        is LoadRemoteContent -> loadContentView(model.title, model.products)
        is LoadLocalContent -> loadContentView(
            getString(R.string.product_main_local_title),
            model.products
        )
        is Navigation -> startActivityForResult<DetailActivity>(CODE) {
            putExtra(
                PRODUCT,
                model.product.id
            )
        }
        is Loading, EmptyState, ErrorState -> { /* BindingAdapterHandling */
        }
    }

    private fun loadContentView(title: String, products: List<Product>) = with(binding) {
        textViewRecentProducts.text = title
        editTextFindProduct.setQuery("", false)
        editTextFindProduct.clearFocus()
        editTextFindProduct.onActionViewCollapsed()
        contentProductsAdapter.products = products.asReversed()
    }

}