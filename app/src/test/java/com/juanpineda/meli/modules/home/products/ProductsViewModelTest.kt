package com.juanpineda.meli.modules.home.products

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.juanpineda.data.server.result.ErrorResponse
import com.juanpineda.data.server.result.SuccessResponse
import com.juanpineda.data.server.result.error.Failure.NetworkConnection
import com.juanpineda.data.server.result.error.Failure.UnknownException
import com.juanpineda.meli.CoroutinesTestRule
import com.juanpineda.meli.callPrivateFun
import com.juanpineda.meli.defaultFakeProducts
import com.juanpineda.meli.ui.modules.home.common.ScopedViewModel
import com.juanpineda.meli.ui.modules.home.products.model.ProductData
import com.juanpineda.meli.ui.modules.home.products.model.ProductSearchType
import com.juanpineda.meli.ui.modules.home.products.viewmodel.ProductsViewModel
import com.juanpineda.usecases.GetFavoriteProducts
import com.juanpineda.usecases.GetProductByName
import com.juanpineda.usecases.GetProducts
import com.juanpineda.usecases.GetProductsByCategory
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.refEq
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProductsViewModelTest {
    @Mock
    lateinit var getProducts: GetProducts

    @Mock
    lateinit var getProductByName: GetProductByName

    @Mock
    lateinit var getProductsByCategory: GetProductsByCategory

    @Mock
    lateinit var getFavoriteProducts: GetFavoriteProducts

    private val observer: Observer<ScopedViewModel.UiModel> = mock()

    private lateinit var sut: ProductsViewModel
    private val productData = ProductData("", ProductSearchType.CATEGORY, "")

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        sut = ProductsViewModel(
            productData,
            getProducts,
            getProductByName,
            getFavoriteProducts,
            getProductsByCategory
        )
    }

    @Test
    fun `endSearch products by category return products`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // given
            given(getProductsByCategory.invoke(ArgumentMatchers.anyString())).willReturn(
                SuccessResponse(
                    defaultFakeProducts
                )
            )
            sut.model.observeForever(observer)
            // when
            sut.callPrivateFun("getProductsByCategory", "")
            // then
            verify(observer).onChanged(
                refEq(
                    ProductsViewModel.LoadProducts(
                        defaultFakeProducts
                    )
                )
            )
        }

    @Test
    fun `endSearch products by category return error`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // given
            given(getProductsByCategory.invoke(ArgumentMatchers.anyString())).willReturn(
                ErrorResponse(
                    UnknownException
                )
            )
            sut.model.observeForever(observer)
            // when
            sut.callPrivateFun("getProductsByCategory", "")
            // then
            verify(observer).onChanged(refEq(ScopedViewModel.UiModel.ErrorState))
        }

    @Test
    fun `endSearch products by category return empty list`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // given
            given(getProductsByCategory.invoke(ArgumentMatchers.anyString())).willReturn(
                SuccessResponse(
                    emptyList()
                )
            )
            sut.model.observeForever(observer)
            // when
            sut.callPrivateFun("getProductsByCategory", "")
            // then
            verify(observer).onChanged(
                refEq(
                    ScopedViewModel.UiModel.EmptyState
                )
            )
        }

    @Test
    fun `endSearchByName products return products`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // given
            val nameToSearch = "celulares"
            given(getProductByName.invoke(ArgumentMatchers.anyString())).willReturn(
                SuccessResponse(
                    defaultFakeProducts
                )
            )
            sut.model.observeForever(observer)
            // when
            sut.callPrivateFun("getProductsByName", nameToSearch)
            // then
            verify(observer).onChanged(
                refEq(
                    ProductsViewModel.LoadProducts(
                        defaultFakeProducts
                    )
                )
            )
        }

    @Test
    fun `endSearchByName products return error`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // given
            val nameToSearch = "celulares"
            given(getProductByName.invoke(ArgumentMatchers.anyString())).willReturn(
                ErrorResponse(
                    NetworkConnection()
                )
            )
            sut.model.observeForever(observer)
            // when
            sut.callPrivateFun("getProductsByName", nameToSearch)
            // then
            verify(observer).onChanged(refEq(ScopedViewModel.UiModel.ErrorState))
        }

    @Test
    fun `endSearchByName products return empty list`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // given
            val nameToSearch = "celulares"
            given(getProductByName.invoke(ArgumentMatchers.anyString())).willReturn(
                SuccessResponse(
                    emptyList()
                )
            )
            sut.model.observeForever(observer)
            // when
            sut.callPrivateFun("getProductsByName", nameToSearch)
            // then
            verify(observer).onChanged(
                refEq(
                    ScopedViewModel.UiModel.EmptyState
                )
            )
        }

    @Test
    fun `getLocalProducts return products`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        // given
        given(getProducts.invoke()).willReturn(defaultFakeProducts)
        sut.model.observeForever(observer)
        // when
        sut.callPrivateFun("getLocalProducts")
        // then
        verify(observer).onChanged(refEq(ProductsViewModel.LoadProducts(defaultFakeProducts)))
    }

    @Test
    fun `getLocalProducts return emptyList`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        // given
        given(getProducts.invoke()).willReturn(emptyList())
        sut.model.observeForever(observer)
        // when
        sut.callPrivateFun("getLocalProducts")
        // then
        verify(observer).onChanged(refEq(ScopedViewModel.UiModel.EmptyState))
    }

    @Test
    fun `getFavoriteProducts return products`() {
        // given
        given(getFavoriteProducts.invoke()).willReturn(flowOf(defaultFakeProducts))
        sut.model.observeForever(observer)
        // when
        sut.callPrivateFun("getFavoriteProducts")
        // then
        verify(observer).onChanged(refEq(ProductsViewModel.LoadProducts(defaultFakeProducts)))
    }

    @Test
    fun `getFavoriteProducts return emptyList`() {
        // given
        given(getFavoriteProducts.invoke()).willReturn(flowOf(emptyList()))
        sut.model.observeForever(observer)
        // when
        sut.callPrivateFun("getFavoriteProducts")
        // then
        verify(observer).onChanged(refEq(ScopedViewModel.UiModel.EmptyState))
    }
}