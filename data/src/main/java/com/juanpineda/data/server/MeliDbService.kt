package com.juanpineda.data.server

import com.juanpineda.data.server.category.MeliDbCategoryResult
import com.juanpineda.data.server.product.MeliDbProductResult
import com.juanpineda.data.server.productdetail.MeliDbProductDetailResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MeliDbService {
    @GET("/sites/MCO/domain_discovery/search")
    suspend fun getPredictiveCategoryAsync(
            @Query("q") query: String
    ): List<MeliDbCategoryResult>

    @GET("sites/MCO/search")
    suspend fun getProductsByCategoryAsync(
            @Query("category") query: String
    ): MeliDbProductResult

    @GET("sites/MCO/search")
    suspend fun getProductsByNameAsync(
            @Query("q") query: String
    ): MeliDbProductResult

    @GET("items/{itemId}")
    suspend fun getProductDetailAsync(
            @Path("itemId") itemId: String
    ): MeliDbProductDetailResult
}