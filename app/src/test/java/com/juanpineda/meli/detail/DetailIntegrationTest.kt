package com.juanpineda.meli.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.juanpineda.data.source.LocalDataSource
import com.juanpineda.meli.*
import com.juanpineda.meli.ui.detail.viewmodel.DetailViewModel
import com.juanpineda.meli.ui.detail.viewmodel.DetailViewModel.UiModel.LoadDetailContent
import com.juanpineda.usecases.FindProductById
import com.juanpineda.usecases.ToggleProductFavorite
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.ArgumentMatchers.refEq

class DetailIntegrationTest : AutoCloseKoinTest() {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val observer: Observer<DetailViewModel.UiModel> = mock()
    private lateinit var localDataSource: FakeLocalDataSource
    private lateinit var vm: DetailViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { (id: String) -> DetailViewModel(id, get(), get()) }
            factory { FindProductById(get()) }
            factory { ToggleProductFavorite(get()) }
        }

        initMockedDi(vmModule)
        vm = get { parametersOf(mockedProduct.id) }
        localDataSource = get<LocalDataSource>() as FakeLocalDataSource
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `observing LiveData finds the Product`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // given
            vm.model.observeForever(observer)
            // then
            verify(observer).onChanged(refEq(LoadDetailContent(defaultFakeProducts[0])))
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `favorite is updated in local data source`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // given
            vm.model.observeForever(observer)
            // when
            vm.onFavoriteClicked()
            // then
            Assert.assertTrue(localDataSource.findById(defaultFakeProducts[0].id).favorite)
        }
}