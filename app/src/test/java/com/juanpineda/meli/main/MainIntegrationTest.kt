package com.juanpineda.meli.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.juanpineda.meli.*
import com.juanpineda.meli.ui.main.viewmodel.MainViewModel
import com.juanpineda.meli.ui.main.viewmodel.MainViewModel.UiModel.*
import com.juanpineda.usecases.GetPredictiveCategory
import com.juanpineda.usecases.GetProducts
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.get
import org.mockito.ArgumentMatchers
import org.mockito.junit.MockitoJUnitRunner
import org.koin.test.AutoCloseKoinTest

@RunWith(MockitoJUnitRunner::class)
class MainIntegrationTest :AutoCloseKoinTest(){

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val observer: Observer<MainViewModel.UiModel> = mock()
    private lateinit var vm: MainViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { MainViewModel(get(), get()) }
            factory { GetProducts(get()) }
            factory { GetPredictiveCategory(get()) }
        }
        initMockedDi(vmModule)
        vm = get()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `data is loaded from server when searching for categories`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        // given
        val query = defaultFakeCategories[1].name
        vm.model.observeForever(observer)

        vm.searching(query)

        verify(observer).onChanged(
                ArgumentMatchers.refEq(Searching(listOf(defaultFakeCategories[1])))
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `data is loaded from server when local source is empty`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val categoryToSearch = defaultFakeCategories[0]
        vm.model.observeForever(observer)

        vm.endSearchByCategory(categoryToSearch)

        verify(observer).onChanged(ArgumentMatchers.refEq(LoadRemoteContent(defaultFakeProducts.let { listOf(it[0], it[4], it[5], it[6]) }, categoryToSearch.name)))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get products by name from server`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        // given
        val nameToSearch = "celular"
        vm.model.observeForever(observer)
        // when
        vm.endSearchByName(nameToSearch)
        //then
        verify(observer).onChanged(ArgumentMatchers.refEq(LoadRemoteContent(defaultFakeProducts.let { listOf(it[0], it[4], it[5], it[6]) }, nameToSearch)))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get getLocalProducts by name from server`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        // when
        vm.model.observeForever(observer)
        //then
        verify(observer).onChanged(ArgumentMatchers.refEq(LoadLocalContent(defaultFakeProducts)))
    }

}