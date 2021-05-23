package com.juanpineda.meli.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.juanpineda.data.server.result.ErrorResponse
import com.juanpineda.data.server.result.error.Failure.NetworkConnection
import com.juanpineda.data.server.result.error.Failure.UnknownException
import com.juanpineda.data.server.result.SuccessResponse
import com.juanpineda.meli.*
import com.juanpineda.meli.ui.main.viewmodel.MainViewModel
import com.juanpineda.meli.ui.main.viewmodel.MainViewModel.UiModel.*
import com.juanpineda.usecases.GetPredictiveCategory
import com.juanpineda.usecases.GetProducts
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class DetailViewModelTest {
    @Mock
    lateinit var getProducts: GetProducts

    @Mock
    lateinit var getPredictiveCategory: GetPredictiveCategory

    private val observer: Observer<MainViewModel.UiModel> = mock()

    private lateinit var sut: MainViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        sut = MainViewModel(getProducts, getPredictiveCategory)
    }

    @Test
    fun `searching categories with not empty query return products`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // given
            given(getProducts.invoke()).willReturn(defaultFakeProducts)
            given(getPredictiveCategory.invoke(ArgumentMatchers.anyString())).willReturn(
                SuccessResponse(defaultFakeCategories)
            )
            sut.model.observeForever(observer)
            // when
            sut.searching("vacaciones")
            // then
            verify(observer).onChanged(refEq(Searching(defaultFakeCategories)))
        }

    @Test
    fun `endSearch products by category return products`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // given
            given(getProducts.invoke()).willReturn(defaultFakeProducts)
            given(getProducts.byCategory(ArgumentMatchers.anyString())).willReturn(
                SuccessResponse(
                    defaultFakeProducts
                )
            )
            sut.model.observeForever(observer)
            // when
            sut.endSearchByCategory(mockedCategory)
            // then
            verify(observer).onChanged(
                refEq(
                    LoadRemoteContent(
                        defaultFakeProducts,
                        mockedCategory.name
                    )
                )
            )
        }

    @Test
    fun `endSearch products by category return error`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // given
            given(getProducts.invoke()).willReturn(defaultFakeProducts)
            given(getProducts.byCategory(ArgumentMatchers.anyString())).willReturn(
                ErrorResponse(
                    UnknownException
                )
            )
            sut.model.observeForever(observer)
            // when
            sut.endSearchByCategory(mockedCategory)
            // then
            verify(observer).onChanged(refEq(ErrorState))
        }

    @Test
    fun `endSearchByName products return products`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // given
            val nameToSearch = "celulares"
            given(getProducts.invoke()).willReturn(emptyList())
            given(getProducts.byName(ArgumentMatchers.anyString())).willReturn(
                SuccessResponse(
                    defaultFakeProducts
                )
            )
            sut.model.observeForever(observer)
            // when
            sut.endSearchByName(nameToSearch)
            // then
            verify(observer).onChanged(refEq(LoadRemoteContent(defaultFakeProducts, nameToSearch)))
        }

    @Test
    fun `endSearchByName products return error`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // given
            val nameToSearch = "celulares"
            given(getProducts.invoke()).willReturn(emptyList())
            given(getProducts.byName(ArgumentMatchers.anyString())).willReturn(
                ErrorResponse(
                    NetworkConnection()
                )
            )
            sut.model.observeForever(observer)
            // when
            sut.endSearchByName(nameToSearch)
            // then
            verify(observer).onChanged(refEq(ErrorState))
        }

    @Test
    fun `getLocalProducts return products`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        // given
        given(getProducts.invoke()).willReturn(defaultFakeProducts)
        // when
        sut.model.observeForever(observer)
        // then
        verify(observer).onChanged(refEq(LoadLocalContent(defaultFakeProducts)))
    }

    @Test
    fun `getLocalProducts return emptyList`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        // given
        given(getProducts.invoke()).willReturn(emptyList())
        // when
        sut.model.observeForever(observer)
        // then
        verify(observer).onChanged(refEq(EmptyState))
    }

    @Test
    fun `onProductClicked call action`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        // given
        given(getProducts.invoke()).willReturn(emptyList())
        sut.model.observeForever(observer)
        // when
        sut.onProductClicked(mockedProduct)
        // then
        verify(observer).onChanged(refEq(Navigation(mockedProduct)))
    }
}