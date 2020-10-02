package com.juanpineda.meli.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.juanpineda.meli.*
import com.juanpineda.meli.ui.main.MainActivityModule
import com.juanpineda.meli.ui.main.viewmodel.MainViewModel
import com.juanpineda.meli.ui.main.viewmodel.MainViewModel.UiModel.*
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
class MainIntegrationTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val observer: Observer<MainViewModel.UiModel> = mock()
    private val component: TestComponent = DaggerTestComponent.factory().create()
    private lateinit var localDataSource: FakeLocalDataSource
    private lateinit var remoteDataSource: FakeRemoteDataSource
    private lateinit var vm: MainViewModel

    @Before
    fun setUp() {
        vm = component.plus(MainActivityModule()).mainViewModel
        localDataSource = component.localDataSource as FakeLocalDataSource
        remoteDataSource = component.remoteDataSource as FakeRemoteDataSource
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