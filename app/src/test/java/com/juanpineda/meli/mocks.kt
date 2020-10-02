package com.juanpineda.meli

import com.juanpineda.domain.Category
import com.juanpineda.domain.Product

val mockedProduct = Product(
        "MCO559474248",
        "Micrófono 3.5mm Solapa, Ideal Para Grabar En Pc, Carro, Gps",
        22500,
        "http://mco-s1-p.mlstatic.com/816764-MCO42688996467_072020-I.jpg"
)

val mockedProductDetail = Product(
        "MCO559474248",
        "Micrófono 3.5mm Solapa, Ideal Para Grabar En Pc, Carro, Gps",
        22500,
        "http://mco-s1-p.mlstatic.com/816764-MCO42688996467_072020-I.jpg",
        "MLA1022",
        250,
        listOf("https://mco-s1-p.mlstatic.com/816764-MCO42688996467_072020-O.jpg", "https://mco-s1-p.mlstatic.com/816764-MCO42688996467_072020-O.jpg"),
        "active",
        false
)

val mockedCategory = Category(
        id = "MLA1055",
        name = "Celulares y Smartphones"
)