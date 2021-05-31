package com.juanpineda.usecases

import com.juanpineda.data.repository.CategoriesRepository
import com.juanpineda.data.server.result.ErrorResponse
import com.juanpineda.data.server.result.SuccessResponse
import com.juanpineda.data.server.result.error.Failure
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
class GetCategoriesTest {
    @Mock
    lateinit var categoriesRepository: CategoriesRepository

    private lateinit var sut: GetCategories

    @Before
    fun setup() {
        sut = GetCategories(categoriesRepository)
    }

    @Test
    fun getCategories_return_success() {
        // given
        val categories = listOf(Category())
        runBlocking {
            given(categoriesRepository.getCategories())
                .willReturn(SuccessResponse(categories))
            // when
            sut.invoke().onSuccess {
                // then
                assertEquals(categories, it)
            }
        }
    }

    @Test
    fun getCategories_return_UnknownException() {
        // given
        val error = Failure.UnknownException
        runBlocking {
            given(categoriesRepository.getCategories())
                .willReturn(ErrorResponse(Failure.UnknownException))
            // when
            sut.invoke().onError {
                //then
                assertEquals(error, failure)
            }
        }
    }

    @Test
    fun getCategories_return_NetworkConnection() {
        // given
        val error = Failure.NetworkConnection()
        runBlocking {
            given(categoriesRepository.getCategories())
                .willReturn(ErrorResponse(error))
            // when
            sut.invoke().onError {
                //then
                assertEquals(error, failure)
            }
        }
    }
}