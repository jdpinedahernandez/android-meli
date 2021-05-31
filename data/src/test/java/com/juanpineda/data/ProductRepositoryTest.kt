package com.juanpineda.data

import com.juanpineda.data.repository.ProductsRepository
import com.juanpineda.data.server.MeliDb
import com.juanpineda.data.server.result.ErrorResponse
import com.juanpineda.data.server.result.SuccessResponse
import com.juanpineda.data.server.result.error.entity.BAD_REQUEST
import com.juanpineda.data.server.result.error.entity.ErrorType.INVALID_QUERY
import com.juanpineda.data.server.result.error.Failure
import com.juanpineda.data.server.result.error.ServerFailure.InvalidValueForQueryParameter
import com.juanpineda.data.server.result.onError
import com.juanpineda.data.server.result.onSuccess
import com.juanpineda.data.source.LocalDataSource
import com.juanpineda.data.source.RemoteDataSource
import com.juanpineda.domain.Category
import com.juanpineda.domain.Product
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProductRepositoryTest {
    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    private lateinit var productsRepository: ProductsRepository

    @Before
    fun setUp() {
        productsRepository =
            ProductsRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun `getProductsByName return list of products`() {
        runBlocking {
            // given
            val nameToSearch = "Micrófono"
            val product = Product(title = "Micrófono 3.5mm Solapa")
            val products = listOf(product, product.copy(title = "Micrófono 10.5mm"))
            given(remoteDataSource.getProductsByName(nameToSearch)).willReturn(
                SuccessResponse(products)
            )
            // when
            productsRepository.getProductsByName(nameToSearch).onSuccess {
                // then
                assertEquals(products, it)
            }

        }
    }

    @Test
    fun `getProductsByName return general error`() {
        runBlocking {
            // given
            val error = Failure.UnknownException
            given(remoteDataSource.getProductsByName(ArgumentMatchers.anyString())).willReturn(
                ErrorResponse(Failure.UnknownException)
            )
            // when
            productsRepository.getProductsByName(ArgumentMatchers.anyString()).onError {
                // then
                assertEquals(error, failure)
            }

        }
    }

    @Test
    fun `getProductsByName return invalid query error`() {
        runBlocking {
            // given
            val httpException = buildHttpExceptionMock(INVALID_QUERY, BAD_REQUEST)
            val expectedError = InvalidValueForQueryParameter
            given(remoteDataSource.getProductsByName(ArgumentMatchers.anyString())).willReturn(
                ErrorResponse(Failure.analyzeException(httpException))
            )
            // when
            productsRepository.getProductsByName(ArgumentMatchers.anyString()).onError {
                // then
                assertEquals(expectedError, failure)
            }

        }
    }

    @Test
    fun `getProducts return list of products`() {
        runBlocking {
            // given
            val products = listOf(Product())
            given(localDataSource.isEmpty()).willReturn(false)
            given(localDataSource.getProducts()).willReturn(products)
            // when
            val result = productsRepository.getProducts()
            // then
            assertEquals(products, result)
        }
    }

    @Test
    fun `getProducts return empty list`() {
        runBlocking {
            // given
            val emptyProducts: List<Product> = emptyList()
            given(localDataSource.isEmpty()).willReturn(true)
            // when
            val result = productsRepository.getProducts()
            // then
            assertEquals(emptyProducts, result)
        }
    }

    @Test
    fun `findById return local product already exists`() {
        runBlocking {
            // given
            val product = Product(id = "MCO559474248")
            given(localDataSource.isProductIsExist(product.id)).willReturn(false)
            given(localDataSource.findById(product.id)).willReturn(product)
            // when
            val result = productsRepository.findById(product.id)
            // then
            assertEquals(product, result)
        }
    }

    @Test
    fun `findById return remote product and saves it`() {
        runBlocking {
            // given
            val product = Product(id = "MCO559474248")
            given(localDataSource.isProductIsExist(product.id)).willReturn(false)
            given(remoteDataSource.getProductDetail(product.id)).willReturn(SuccessResponse(product))
            given(localDataSource.findById(product.id)).willReturn(product)
            // when
            val result = productsRepository.findById(product.id)
            // then
            assertEquals(product, result)
        }
    }

    @Test
    fun `findById return remote no product`() {
        runBlocking {
            // given
            val product = Product(id = "MCO559474248")
            val emptyProduct = Product()
            given(localDataSource.isProductIsExist(product.id)).willReturn(false)
            given(remoteDataSource.getProductDetail(product.id)).willReturn(ErrorResponse(Failure.UnknownException))
            given(localDataSource.findById(product.id)).willReturn(emptyProduct)
            // when
            val result = productsRepository.findById(product.id)
            // then
            assertEquals(emptyProduct, result)
        }
    }

    @Test
    fun `update product local data source`() {
        runBlocking {
            // given
            val product = Product(id = "MCO559474248")
            // when
            productsRepository.update(product)
            // then
            verify(localDataSource).update(product)
        }
    }

}