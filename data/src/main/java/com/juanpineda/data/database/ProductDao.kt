package com.juanpineda.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product")
    fun getAll(): List<Product>

    @Query("SELECT * FROM Product WHERE title LIKE '%' ||:title|| '%'")
    fun getProductByTitle(title: String): List<Product>

    @Query("SELECT * FROM Product WHERE id = :id")
    fun findById(id: String): Product?

    @Query("SELECT COUNT(id) FROM Product")
    fun productCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertProducts(products: List<Product>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertProduct(product: Product)

    @Query("SELECT EXISTS(SELECT * FROM Product WHERE id = :id)")
    fun isProductIsExist(id: String): Boolean

    @Update
    fun updateProduct(product: Product)

    @Query("SELECT * FROM Product WHERE favorite")
    fun getFavoriteProducts(): Flow<List<Product>>
}