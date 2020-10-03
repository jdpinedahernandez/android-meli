package com.juanpineda.usecases

import com.juanpineda.data.repository.ProductsRepository
import com.juanpineda.domain.Product
import com.juanpineda.result.ErrorResponse
import com.juanpineda.result.Failure.NetworkConnection
import com.juanpineda.result.Failure.UnknownException
import com.juanpineda.result.SuccessResponse
import com.juanpineda.result.onError
import com.juanpineda.result.onSuccess
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
                assertEquals(error, it.exception)
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
                assertEquals(error, it.exception)
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
                assertEquals(error, it.exception)
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
                assertEquals(error, it.exception)
            }
        }
    }
}