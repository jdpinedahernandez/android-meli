package com.juanpineda.meli.modules.home.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.juanpineda.meli.*
import com.juanpineda.meli.ui.modules.home.common.ScopedViewModel
import com.juanpineda.meli.ui.modules.home.search.SearchFragmentModule
import com.juanpineda.meli.ui.modules.home.search.viewmodel.SearchViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SearchIntegrationTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val observer: Observer<ScopedViewModel.UiModel> = mock()
    private val component: TestComponent = DaggerTestComponent.factory().create()
    private lateinit var localDataSource: FakeLocalDataSource
    private lateinit var remoteDataSource: FakeRemoteDataSource
    private lateinit var vm: SearchViewModel

    @Before
    fun setUp() {
        vm = component.plus(SearchFragmentModule()).searchViewModel
        localDataSource = component.localDataSource as FakeLocalDataSource
        remoteDataSource = component.remoteDataSource as FakeRemoteDataSource
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `data is loaded from server when searching for categories`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // given
            val query = defaultFakeCategories[1].name
            vm.model.observeForever(observer)

            vm.searchingPredictiveCategory(query)

            verify(observer).onChanged(
                ArgumentMatchers.refEq(
                    SearchViewModel.LoadPredictiveCategory(
                        listOf(
                            defaultFakeCategories[1]
                        )
                    )
                )
            )
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `data is loaded from server when call categories service`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val categories = defaultFakeCategories
            vm.model.observeForever(observer)

            verify(observer).onChanged(
                ArgumentMatchers.refEq(
                    SearchViewModel.LoadCategories(
                        categories
                    )
                )
            )
        }
}