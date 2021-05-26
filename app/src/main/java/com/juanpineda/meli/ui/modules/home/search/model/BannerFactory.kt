package com.juanpineda.meli.ui.modules.home.search.model

import com.juanpineda.domain.Category
import com.juanpineda.meli.R

class BannerFactory {
    fun getList() = listOf(
        BannerEntity(
            "Celulares y Teléfonos",
            "subtitulo",
            R.drawable.ic_phone,
            Category(id = "MCO1051")
        ),
        BannerEntity(
            "Deportes y Fitness",
            "subtitulo",
            R.drawable.ic_sports,
            Category(id = "MCO1276")
        ),
        BannerEntity(
            "Hogar y Muebles",
            "subtitulo",
            R.drawable.ic_phone,
            Category(id = "MCO1574")
        )
    )
}

data class BannerEntity(
    val title: String,
    val subtitle: String,
    val image: Int = 0,
    val category: Category
)