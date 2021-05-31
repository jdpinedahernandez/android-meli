package com.juanpineda.meli.modules.home.products

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.juanpineda.meli.*
import com.juanpineda.meli.ui.modules.home.common.ScopedViewModel
import com.juanpineda.meli.ui.modules.home.products.ProductsFragmentModule
import com.juanpineda.meli.ui.modules.home.products.model.ProductData
import com.juanpineda.meli.ui.modules.home.products.model.ProductSearchType
import com.juanpineda.meli.ui.modules.home.products.viewmodel.ProductsViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class ProductsIntegrationTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val observer: Observer<ScopedViewModel.UiModel> = mock()
    private val component: TestComponent = DaggerTestComponent.factory().create()
    private lateinit var localDataSource: FakeLocalDataSource
    private lateinit var remoteDataSource: FakeRemoteDataSource
    private lateinit var vm: ProductsViewModel

    private fun setUp(ProductData: ProductData) {
        vm = component.plus(ProductsFragmentModule(ProductData)).productsViewModel
        localDataSource = component.localDataSource as FakeLocalDataSource
        remoteDataSource = component.remoteDataSource as FakeRemoteDataSource
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get products by specific category search`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // when
            setUp(ProductData(defaultFakeCategories[0].id, ProductSearchType.CATEGORY, ""))
            vm.model.observeForever(observer)
            // then
            verify(observer).onChanged(ArgumentMatchers.refEq(
                ProductsViewModel.LoadProducts(
                    defaultFakeProducts.let {
                        listOf(
                            it[0],
                            it[4],
                            it[5],
                            it[6]
                        )
                    }
                )
            ))
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `get products by name from server`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        // given
        val nameToSearch = "celular"
        // when
        setUp(ProductData(nameToSearch, ProductSearchType.PRODUCT, ""))
        vm.model.observeForever(observer)
        //then
        verify(observer).onChanged(ArgumentMatchers.refEq(
            ProductsViewModel.LoadProducts(
                defaultFakeProducts.let { listOf(it[0], it[4], it[5], it[6]) }
            )
        ))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get Local Products`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        // given
        setUp(ProductData("", ProductSearchType.VIEWED, ""))
        vm.model.observeForever(observer)
        //then
        verify(observer).onChanged(
            ArgumentMatchers.refEq(
                ProductsViewModel.LoadProducts(defaultFakeProducts)
            )
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get favorite Products`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        // given
        setUp(ProductData("", ProductSearchType.FAVORITE, ""))
        vm.model.observeForever(observer)
        //then
        verify(observer).onChanged(
            ArgumentMatchers.refEq(
                ProductsViewModel.LoadProducts(defaultFakeProducts.let {
                    listOf(
                        it[0],
                        it[5],
                        it[6]
                    )
                })
            )
        )
    }
}