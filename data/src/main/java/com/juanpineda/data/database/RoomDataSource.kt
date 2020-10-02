package com.juanpineda.data.database

import com.juanpineda.data.source.LocalDataSource
import com.juanpineda.data.toDomainProduct
import com.juanpineda.data.toRoomProduct
import com.juanpineda.domain.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(db: ProductDatabase) : LocalDataSource {

    private val productDao = db.productDao()

    override suspend fun isEmpty(): Boolean =
            withContext(Dispatchers.IO) { productDao.productCount() <= 0 }

    override suspend fun saveProducts(products: List<Product>) {
        withContext(Dispatchers.IO) { productDao.insertProducts(products.map { it.toRoomProduct() }) }
    }

    override suspend fun saveProduct(product: Product) {
        withContext(Dispatchers.IO) { productDao.insertProduct(product.toRoomProduct()) }
    }

    override suspend fun getProducts(): List<Product> = withContext(Dispatchers.IO) {
        productDao.getAll().map { it.toDomainProduct() }
    }

    override suspend fun getProductsByTitle(title: String): List<Product> = withContext(Dispatchers.IO) {
        productDao.getProductByTitle(title).map { it.toDomainProduct() }
    }

    override suspend fun findById(id: String): Product = withContext(Dispatchers.IO) {
        productDao.findById(id).toDomainProduct()
    }

    override suspend fun isProductIsExist(id: String): Boolean = withContext(Dispatchers.IO) {
        productDao.isProductIsExist(id)
    }

    override suspend fun update(product: Product) {
        withContext(Dispatchers.IO) { productDao.updateProduct(product.toRoomProduct()) }
    }
}