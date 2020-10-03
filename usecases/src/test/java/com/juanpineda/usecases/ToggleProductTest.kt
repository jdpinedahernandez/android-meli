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
class ToggleProductTest {
    @Mock
    lateinit var productsRepository: ProductsRepository

    private lateinit var sut: ToggleProductFavorite

    @Before
    fun setup() {
        sut = ToggleProductFavorite(productsRepository)
    }

    @Test
    fun toggleProductFavorite() {
        // given
        val product = Product()
        runBlocking {
            given(productsRepository.findById(ArgumentMatchers.anyString()))
                .willReturn(product)
            given(productsRepository.update(product.copy(favorite = product.favorite.not())))
                .willReturn(Unit)
            // when
            val result = sut.invoke(ArgumentMatchers.anyString())
            // then
            assertEquals(product.copy(favorite = product.favorite.not()), result)
        }
    }
}