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
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FindProductByIdTest {
    @Mock
    lateinit var productsRepository: ProductsRepository

    private lateinit var sut: FindProductById

    @Before
    fun setup() {
        sut = FindProductById(productsRepository)
    }

    @Test
    fun findProductById() {
        // given
        val product = Product()
        runBlocking {
            given(productsRepository.findById(ArgumentMatchers.anyString()))
                .willReturn(product)
            // when
            val result = sut.invoke(ArgumentMatchers.anyString())
            // then
            assertEquals(product, result)
        }
    }
}