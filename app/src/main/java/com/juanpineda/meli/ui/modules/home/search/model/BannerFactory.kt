package com.juanpineda.meli.ui.modules.home.search.model

import android.content.Context
import com.juanpineda.domain.Category
import com.juanpineda.meli.R

class BannerFactory(val context: Context) {
    fun getList() = listOf(
        BannerEntity(
            context.getString(R.string.banner_first_category_description),
            R.drawable.ic_phone,
            Category(
                context.getString(R.string.banner_first_category_id),
                context.getString(R.string.banner_first_category_name),
            )
        ),
        BannerEntity(
            context.getString(R.string.banner_second_category_description),
            R.drawable.ic_sports,
            Category(
                context.getString(R.string.banner_second_category_id),
                context.getString(R.string.banner_second_category_name),
            )
        ),
        BannerEntity(
            context.getString(R.string.banner_third_category_description),
            R.drawable.ic_home,
            Category(
                context.getString(R.string.banner_third_category_id),
                context.getString(R.string.banner_third_category_name),
            )
        )
    )
}

data class BannerEntity(
    val description: String,
    val image: Int = 0,
    val category: Category
)