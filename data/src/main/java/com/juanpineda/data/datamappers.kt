package com.juanpineda.data

import com.juanpineda.domain.Category
import com.juanpineda.domain.Product
import com.juanpineda.data.database.Product as DomainProduct
import com.juanpineda.data.server.product.Product as ServerProduct
import com.juanpineda.data.server.category.MeliDbCategoryResult as ServerCategory
import com.juanpineda.data.server.productdetail.MeliDbProductDetailResult as ServerProductDetail
import com.juanpineda.data.server.productdetail.Picture as ServerProductDetailPicture

fun Product.toRoomProduct(): DomainProduct =
        DomainProduct(
                id,
                title,
                price,
                thumbnail,
                categoryId,
                availableQuantity,
                pictures,
                status,
                favorite
        )

fun DomainProduct.toDomainProduct(): Product =
        Product(
                id,
                title,
                price,
                thumbnail,
                categoryId,
                availableQuantity,
                pictures,
                status,
                favorite
        )

fun ServerProduct.toDomainProduct(): Product =
        Product(
                id,
                title,
                price,
                thumbnail,
                categoryId
        )

fun ServerProductDetail.toDomainProduct(): Product =
        Product(
                id,
                title,
                price,
                thumbnail,
                categoryId,
                availableQuantity,
                pictures.map { it.toDomainProductPicture() },
                status
        )

fun ServerProductDetailPicture.toDomainProductPicture() = secureUrl

fun ServerCategory.toDomainCategory(): Category =
        Category(
                categoryId,
                categoryName
        )