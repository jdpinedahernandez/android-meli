package com.juanpineda.usecases

import com.juanpineda.data.repository.CategoriesRepository
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
class GetProductsByCategoryTest {
    @Mock
    lateinit var categoriesRepository: CategoriesRepository

    private lateinit var sut: GetProductsByCategory

    @Before
    fun setup() {
        sut = GetProductsByCategory(categoriesRepository)
    }

    @Test
    fun getProductsByCategory_return_success() {
        // given
        val products = listOf(Product())
        runBlocking {
            given(categoriesRepository.getProductsByCategory(ArgumentMatchers.anyString()))
                .willReturn(SuccessResponse(products))
            // when
            sut.invoke(ArgumentMatchers.anyString()).onSuccess {
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
            given(categoriesRepository.getProductsByCategory(ArgumentMatchers.anyString()))
                .willReturn(ErrorResponse(UnknownException))
            // when
            sut.invoke(ArgumentMatchers.anyString()).onError {
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
            given(categoriesRepository.getProductsByCategory(ArgumentMatchers.anyString()))
                .willReturn(ErrorResponse(error))
            // when
            sut.invoke(ArgumentMatchers.anyString()).onError {
                //then
                assertEquals(error, failure)
            }
        }
    }
}