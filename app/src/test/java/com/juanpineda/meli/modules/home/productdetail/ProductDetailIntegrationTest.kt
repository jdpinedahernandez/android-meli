package com.juanpineda.meli.modules.home.productdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.juanpineda.meli.*
import com.juanpineda.meli.ui.modules.home.productdetail.ProductDetailFragmentModule
import com.juanpineda.meli.ui.modules.home.productdetail.viewmodel.ProductDetailViewModel
import com.juanpineda.meli.ui.modules.home.productdetail.viewmodel.ProductDetailViewModel.UiModel.LoadDetailContent
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.refEq

class ProductDetailIntegrationTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val observer: Observer<ProductDetailViewModel.UiModel> = mock()
    private val component: TestComponent = DaggerTestComponent.factory().create()
    private lateinit var localDataSource: FakeLocalDataSource
    private lateinit var vm: ProductDetailViewModel

    @Before
    fun setUp() {
        vm = component.plus(ProductDetailFragmentModule(defaultFakeProducts[1].id)).productDetailViewModel
        localDataSource = component.localDataSource as FakeLocalDataSource
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `observing LiveData finds the Product`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        // given
        vm.model.observeForever(observer)
        // then
        verify(observer).onChanged(refEq(LoadDetailContent(defaultFakeProducts[1])))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `favorite is updated in local data source`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        // given
        vm.model.observeForever(observer)
        // when
        vm.onFavoriteClicked()
        // then
        Assert.assertTrue(localDataSource.findById(defaultFakeProducts[1].id).favorite)
    }
}