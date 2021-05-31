package com.juanpineda.usecases

import com.juanpineda.data.repository.ProductsRepository
import com.juanpineda.domain.Product
import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetFavoriteProductsTest {
    @Mock
    lateinit var productsRepository: ProductsRepository

    private lateinit var sut: GetFavoriteProducts

    @Before
    fun setup() {
        sut = GetFavoriteProducts(productsRepository)
    }

    @Test
    fun getFavoriteProducts() {
        // given
        val products = listOf(Product())
        runBlocking {
            given(productsRepository.getFavoriteProducts())
                .willReturn(flowOf(products))
            // when
            sut.invoke().collect {
                // then
                assertEquals(products, it)
            }
        }
    }
}