package com.juanpineda.usecases

import com.juanpineda.data.repository.CategoriesRepository
import com.juanpineda.data.server.result.ErrorResponse
import com.juanpineda.data.server.result.error.Failure.NetworkConnection
import com.juanpineda.data.server.result.error.Failure.UnknownException
import com.juanpineda.data.server.result.SuccessResponse
import com.juanpineda.data.server.result.onError
import com.juanpineda.data.server.result.onSuccess
import com.juanpineda.domain.Category
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
    lateinit var categoriesRepository: CategoriesRepository

    private lateinit var sut: GetPredictiveCategory

    @Before
    fun setup() {
        sut = GetPredictiveCategory(categoriesRepository)
    }

    @Test
    fun getPredictiveCategory_return_success() {
        // given
        val categories = listOf(Category())
        runBlocking {
            given(categoriesRepository.getPredictiveCategory(ArgumentMatchers.anyString()))
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
            given(categoriesRepository.getPredictiveCategory(ArgumentMatchers.anyString()))
                .willReturn(ErrorResponse(UnknownException))
            // when
            sut.invoke(ArgumentMatchers.anyString()).onError {
                //then
                assertEquals(error, failure)
            }
        }
    }

    @Test
    fun getPredictiveCategory_return_NetworkConnection_error() {
        // given
        val error = NetworkConnection()
        runBlocking {
            given(categoriesRepository.getPredictiveCategory(ArgumentMatchers.anyString()))
                .willReturn(ErrorResponse(error))
            // when
            sut.invoke(ArgumentMatchers.anyString()).onError {
                //then
                assertEquals(error, failure)
            }
        }
    }
}