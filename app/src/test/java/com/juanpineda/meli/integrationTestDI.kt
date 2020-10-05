package com.juanpineda.meli

import com.juanpineda.data.source.LocalDataSource
import com.juanpineda.data.source.RemoteDataSource
import com.juanpineda.domain.Category
import com.juanpineda.domain.Product
import com.juanpineda.result.ResultHandler
import com.juanpineda.result.SuccessResponse
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun initMockedDi(vararg modules: Module) {
    startKoin {
        modules(listOf(mockedAppModule, dataModule) + modules)
    }
}

private val mockedAppModule = module {
    single(named("apiKey")) { "12456" }
    single<LocalDataSource> { FakeLocalDataSource() }
    single<RemoteDataSource> { FakeRemoteDataSource() }
    single { Dispatchers.Unconfined }
}

class FakeLocalDataSource : LocalDataSource {

    private var products: List<Product> = defaultFakeProducts

    private var product = mockedProduct

    override suspend fun isEmpty() = products.isEmpty()

    override suspend fun saveProducts(products: List<Product>) {
        this.products = products
    }

    override suspend fun saveProduct(product: Product) {
        this.product = product
    }

    override suspend fun getProducts(): List<Product> = products

    override suspend fun getProductsByTitle(title: String): List<Product> =
        products.filter { it.title == title }

    override suspend fun findById(id: String): Product = products.first { it.id == id }

    override suspend fun isProductIsExist(id: String): Boolean = products.any { it.id == id }

    override suspend fun update(product: Product) {
        products = products.filterNot { it.id == product.id } + product
    }
}

class FakeRemoteDataSource : RemoteDataSource {

    private var products = defaultFakeProducts

    private var categories = defaultFakeCategories

    override suspend fun getPredictiveCategory(query: String): ResultHandler<List<Category>> =
        SuccessResponse(categories.filter { it.name == query })

    override suspend fun getProductsByCategory(query: String) =
        SuccessResponse(products.filter { it.categoryId == query })

    override suspend fun getProductsByName(query: String): ResultHandler<List<Product>> =
        SuccessResponse(products.filter { it.title.contains(query) })

    override suspend fun getProductDetail(itemId: String) = SuccessResponse(mockedProduct)
}

val defaultFakeProducts = listOf(
    mockedProduct.copy("MCO559474248", "celular huawei", categoryId = "MLA1055"),
    mockedProduct.copy("MCO559474249", "vehiculo mazda", categoryId = "MLA1032"),
    mockedProduct.copy("MCO559474250", "sombrilla roja grande", categoryId = "MLA1025"),
    mockedProduct.copy("MCO559474267", "colcha para catre", categoryId = "MLA1051"),
    mockedProduct.copy("MCO559474248", "celular iphone", categoryId = "MLA1055"),
    mockedProduct.copy("MCO559474248", "repuestos para celulares", categoryId = "MLA1055"),
    mockedProduct.copy("MCO559474248", "audifonos de celulares", categoryId = "MLA1055")
)

val defaultFakeCategories = listOf(
    mockedCategory.copy(),
    mockedCategory.copy("MLA1056", "Computadores"),
    mockedCategory.copy("MLA1057", "Perfumes"),
    mockedCategory.copy("MLA1058", "Aeroplanos")
)