package com.juanpineda.usecases

import com.juanpineda.data.repository.ProductsRepository
import com.juanpineda.domain.Category
import com.juanpineda.result.*
import com.juanpineda.result.Failure.NetworkConnection
import com.juanpineda.result.Failure.UnknownException
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
class GetPredictiveCategoryTest {
    @Mock
    lateinit var productsRepository: ProductsRepository

    private lateinit var sut: GetPredictiveCategory

    @Before
    fun setup() {
        sut = GetPredictiveCategory(productsRepository)
    }

    @Test
    fun getPredictiveCategory_return_success() {
        // given
        val categories = listOf(Category())
        runBlocking {
            given(productsRepository.getPredictiveCategory(ArgumentMatchers.anyString()))
                .willReturn(SuccessResponse(categories))
            // when
            sut.invoke(ArgumentMatchers.anyString()).onSuccess {
                //then
                assertEquals(categories, it)
            }
        }
    }

    @Test
    fun getPredictiveCategory_return_UnknownException_error() {
        // given
        val error = UnknownException
        runBlocking {
            given(productsRepository.getPredictiveCategory(ArgumentMatchers.anyString()))
                .willReturn(ErrorResponse(UnknownException))
            // when
            sut.invoke(ArgumentMatchers.anyString()).onError {
                //then
                assertEquals(error, it.exception)
            }
        }
    }

    @Test
    fun getPredictiveCategory_return_NetworkConnection_error() {
        // given
        val error = NetworkConnection()
        runBlocking {
            given(productsRepository.getPredictiveCategory(ArgumentMatchers.anyString()))
                .willReturn(ErrorResponse(error))
            // when
            sut.invoke(ArgumentMatchers.anyString()).onError {
                //then
                assertEquals(error, it.exception)
            }
        }
    }
}