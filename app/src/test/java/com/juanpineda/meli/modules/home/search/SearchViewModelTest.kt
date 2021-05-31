package com.juanpineda.meli.modules.home.search


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.juanpineda.data.server.result.ErrorResponse
import com.juanpineda.data.server.result.SuccessResponse
import com.juanpineda.data.server.result.error.Failure
import com.juanpineda.meli.CoroutinesTestRule
import com.juanpineda.meli.defaultFakeCategories
import com.juanpineda.meli.ui.modules.home.common.ScopedViewModel
import com.juanpineda.meli.ui.modules.home.search.viewmodel.SearchViewModel
import com.juanpineda.usecases.GetCategories
import com.juanpineda.usecases.GetPredictiveCategory
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
class SearchViewModelTest {

    @Mock
    lateinit var getCategories: GetCategories

    @Mock
    lateinit var getPredictiveCategory: GetPredictiveCategory

    private val observer: Observer<ScopedViewModel.UiModel> = mock()

    private lateinit var sut: SearchViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        sut = SearchViewModel(getPredictiveCategory, getCategories)
    }

    @Test
    fun `searching predictive categories with not empty query return categories`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // given
            given(getPredictiveCategory.invoke(ArgumentMatchers.anyString())).willReturn(
                SuccessResponse(defaultFakeCategories)
            )
            sut.model.observeForever(observer)
            // when
            sut.searchingPredictiveCategory("vacaciones")
            // then
            verify(observer).onChanged(
                refEq(
                    SearchViewModel.LoadPredictiveCategory(
                        defaultFakeCategories
                    )
                )
            )
        }

    @Test
    fun `searching predictive categories with not empty query return empty state`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // given
            sut.model.observeForever(observer)
            // when
            sut.searchingPredictiveCategory("")
            // then
            verify(observer).onChanged(
                refEq(
                    ScopedViewModel.UiModel.EmptyState
                )
            )
        }

    @Test
    fun `get categories with not empty query return categories`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // given
            given(getCategories.invoke()).willReturn(
                SuccessResponse(defaultFakeCategories)
            )
            sut.model.observeForever(observer)
            // when
            sut.getCategories()
            // then
            verify(observer).onChanged(
                refEq(
                    SearchViewModel.LoadCategories(
                        defaultFakeCategories
                    )
                )
            )
        }

    @Test
    fun `get categories with not empty query return error`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // given
            given(getCategories.invoke()).willReturn(
                ErrorResponse(
                    Failure.UnknownException
                )
            )
            sut.model.observeForever(observer)
            // when
            sut.getCategories()
            // then
            verify(observer).onChanged(
                refEq(
                    ScopedViewModel.UiModel.ErrorState
                )
            )
        }
}