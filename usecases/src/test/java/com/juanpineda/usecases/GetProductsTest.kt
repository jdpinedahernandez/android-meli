package com.juanpineda.usecases

import com.juanpineda.data.repository.ProductsRepository
import com.juanpineda.data.server.result.*
import com.juanpineda.data.server.result.error.Failure.NetworkConnection
import com.juanpineda.data.server.result.error.Failure.UnknownException
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

    @Test
    fun getProductsByCategory_return_success() {
        // given
        val products = listOf(Product())
        runBlocking {
            given(productsRepository.getProductsByCategory(ArgumentMatchers.anyString()))
                .willReturn(SuccessResponse(products))
            // when
            sut.byCategory(ArgumentMatchers.anyString()).onSuccess {
                //then
                assertEquals(products, it)
            }
        }
    }

    @Test
    fun getProductsByCategory_return_UnknownException_error() {
        // given
        val error = UnknownException
        runBlocking {
            given(productsRepository.getProductsByCategory(ArgumentMatchers.anyString()))
                .willReturn(ErrorResponse(UnknownException))
            // when
            sut.byCategory(ArgumentMatchers.anyString()).onError {
                //then
                assertEquals(error, failure)
            }
        }
    }

    @Test
    fun getProductsByCategory_return_NetworkConnection_error() {
        // given
        val error = NetworkConnection()
        runBlocking {
            given(productsRepository.getProductsByCategory(ArgumentMatchers.anyString()))
                .willReturn(ErrorResponse(error))
            // when
            sut.byCategory(ArgumentMatchers.anyString()).onError {
                //then
                assertEquals(error, failure)
            }
        }
    }

    @Test
    fun getProductsByName_return_success() {
        // given
        val products = listOf(Product())
        runBlocking {
            given(productsRepository.getProductsByName(ArgumentMatchers.anyString()))
                .willReturn(SuccessResponse(products))
            // when
            sut.byName(ArgumentMatchers.anyString()).onSuccess {
                //then
                assertEquals(products, it)
            }
        }
    }

    @Test
    fun getProductsByName_return_UnknownException_error() {
        // given
        val error = UnknownException
        runBlocking {
            given(productsRepository.getProductsByName(ArgumentMatchers.anyString()))
                .willReturn(ErrorResponse(UnknownException))
            // when
            sut.byName(ArgumentMatchers.anyString()).onError {
                //then
                assertEquals(error, failure)
            }
        }
    }

    @Test
    fun getProductsByName_return_NetworkConnection_error() {
        // given
        val error = NetworkConnection()
        runBlocking {
            given(productsRepository.getProductsByName(ArgumentMatchers.anyString()))
                .willReturn(ErrorResponse(error))
            // when
            sut.byName(ArgumentMatchers.anyString()).onError {
                //then
                assertEquals(error, failure)
            }
        }
    }
}