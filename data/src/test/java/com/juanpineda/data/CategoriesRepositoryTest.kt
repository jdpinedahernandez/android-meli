package com.juanpineda.data

import com.juanpineda.data.repository.CategoriesRepository
import com.juanpineda.data.server.result.ErrorResponse
import com.juanpineda.data.server.result.SuccessResponse
import com.juanpineda.data.server.result.error.Failure
import com.juanpineda.data.server.result.error.ServerFailure.InvalidValueForQueryParameter
import com.juanpineda.data.server.result.error.entity.BAD_REQUEST
import com.juanpineda.data.server.result.error.entity.ErrorType.INVALID_QUERY
import com.juanpineda.data.server.result.onError
import com.juanpineda.data.server.result.onSuccess
import com.juanpineda.data.source.RemoteDataSource
import com.juanpineda.domain.Category
import com.juanpineda.domain.Product
import com.nhaarman.mockitokotlin2.given
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CategoriesRepositoryTest {

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    private lateinit var categoriesRepository: CategoriesRepository

    @Before
    fun setUp() {
        categoriesRepository =
            CategoriesRepository(remoteDataSource)
    }

    @Test
    fun `getPredictiveCategory return list of categories`() {
        runBlocking {
            // given
            val categories = listOf(Category(id = "MLA1055"))
            given(remoteDataSource.getPredictiveCategory(categories.first().id)).willReturn(
                SuccessResponse(categories)
            )
            // when
            categoriesRepository.getPredictiveCategory(categories.first().id).onSuccess {
                // then
                assertEquals(categories, it)
            }

        }
    }

    @Test
    fun `getPredictiveCategory return generic error`() {
        runBlocking {
            // given
            val error = Failure.UnknownException
            given(remoteDataSource.getPredictiveCategory(ArgumentMatchers.anyString())).willReturn(
                ErrorResponse(Failure.UnknownException)
            )
            // when
            categoriesRepository.getPredictiveCategory(ArgumentMatchers.anyString()).onError {
                // then
                assertEquals(error, failure)
            }

        }
    }

    @Test
    fun `getPredictiveCategory return invalid query error`() {
        runBlocking {
            // given
            val httpException = buildHttpExceptionMock(INVALID_QUERY, BAD_REQUEST)
            val expectedError = InvalidValueForQueryParameter
            given(remoteDataSource.getPredictiveCategory(ArgumentMatchers.anyString())).willReturn(
                ErrorResponse(Failure.analyzeException(httpException))
            )
            // when
            categoriesRepository.getPredictiveCategory(ArgumentMatchers.anyString()).onError {
                // then
                assertEquals(expectedError, failure)
            }

        }
    }

    @Test
    fun `getProductsByCategory return list of products`() {
        runBlocking {
            // given
            val category = Category(id = "MLA1055")
            val products = listOf(Product(id = "MCO559474248"))
            given(remoteDataSource.getProductsByCategory(category.id)).willReturn(
                SuccessResponse(products)
            )
            // when
            categoriesRepository.getProductsByCategory(category.id).onSuccess {
                // then
                assertEquals(products, it)
            }

        }
    }

    @Test
    fun `getProductsByCategory return general error`() {
        runBlocking {
            // given
            val error = Failure.UnknownException
            given(remoteDataSource.getProductsByCategory(ArgumentMatchers.anyString())).willReturn(
                ErrorResponse(Failure.UnknownException)
            )
            // when
            categoriesRepository.getProductsByCategory(ArgumentMatchers.anyString()).onError {
                // then
                assertEquals(error, failure)
            }

        }
    }

    @Test
    fun `getProductsByCategory return invalid query error`() {
        runBlocking {
            // given
            val httpException = buildHttpExceptionMock(INVALID_QUERY, BAD_REQUEST)
            val expectedError = InvalidValueForQueryParameter
            given(remoteDataSource.getProductsByCategory(ArgumentMatchers.anyString())).willReturn(
                ErrorResponse(Failure.analyzeException(httpException))
            )
            // when
            categoriesRepository.getProductsByCategory(ArgumentMatchers.anyString()).onError {
                // then
                assertEquals(expectedError, failure)
            }

        }
    }
}