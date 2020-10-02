package com.juanpineda.meli.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.juanpineda.meli.CoroutinesTestRule
import com.juanpineda.meli.mockedProduct
import com.juanpineda.meli.mockedProductDetail
import com.juanpineda.meli.ui.detail.viewmodel.DetailViewModel
import com.juanpineda.meli.ui.detail.viewmodel.DetailViewModel.UiModel.LoadDetailContent
import com.juanpineda.meli.ui.detail.viewmodel.DetailViewModel.UiModel.LoadFavoriteContent
import com.juanpineda.usecases.FindProductById
import com.juanpineda.usecases.ToggleProductFavorite
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
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @Mock
    lateinit var findProductById: FindProductById

    @Mock
    lateinit var toggleProductFavorite: ToggleProductFavorite

    private val observer: Observer<DetailViewModel.UiModel> = mock()

    private lateinit var sut: DetailViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        sut = DetailViewModel(mockedProduct.id, findProductById, toggleProductFavorite)
    }


    @Test
    fun `findProduct return products`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        // given
        given(findProductById.invoke(mockedProduct.id)).willReturn(mockedProductDetail)
        // when
        sut.model.observeForever(observer)
        // then
        verify(observer).onChanged(ArgumentMatchers.refEq(LoadDetailContent(mockedProductDetail)))
    }

    @Test
    fun `onFavoriteClicked call action`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        // given
        val product = mockedProductDetail.copy(favorite = mockedProductDetail.favorite.not())
        given(findProductById.invoke(mockedProductDetail.id)).willReturn(mockedProductDetail)
        given(toggleProductFavorite.invoke(mockedProductDetail.id)).willReturn(product)
        sut.model.observeForever(observer)
        // when
        sut.onFavoriteClicked()
        // then
        verify(observer).onChanged(ArgumentMatchers.refEq(LoadFavoriteContent(product)))
    }
}