package com.juanpineda.usecases

import com.juanpineda.data.repository.ProductsRepository
import com.juanpineda.domain.Product
import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetProductsTest {
    @Mock
    lateinit var productsRepository: ProductsRepository

    private lateinit var sut: GetProducts

    @Before
    fun setup() {
        sut = GetProducts(productsRepository)
    }

    @Test
    fun getProducts() {
        // given
        val products = listOf(Product())
        runBlocking {
            given(productsRepository.getProducts())
                .willReturn(products)
            // when
            val result = sut.invoke()
            //then
            assertEquals(products, result)
        }
    }
}