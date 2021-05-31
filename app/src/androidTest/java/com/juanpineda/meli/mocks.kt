package com.juanpineda.meli


val categories = """
[{"id":"MCO1747","name":"Accesorios para Vehículos"},{"id":"MCO441917","name":"Agro"},{"id":"MCO1403","name":"Alimentos y Bebidas"},{"id":"MCO1071","name":"Animales y Mascotas"},{"id":"MCO1367","name":"Antigüedades y Colecciones"},{"id":"MCO1368","name":"Arte, Papelería y Mercería"},{"id":"MCO1384","name":"Bebés"},{"id":"MCO1246","name":"Belleza y Cuidado Personal"},{"id":"MCO40433","name":"Boletas para Espectáculos"},{"id":"MCO1039","name":"Cámaras y Accesorios"},{"id":"MCO1743","name":"Carros, Motos y Otros"},{"id":"MCO1051","name":"Celulares y Teléfonos"},{"id":"MCO1648","name":"Computación"},{"id":"MCO1144","name":"Consolas y Videojuegos"},{"id":"MCO172890","name":"Construcción"},{"id":"MCO1276","name":"Deportes y Fitness"},{"id":"MCO5726","name":"Electrodomésticos"},{"id":"MCO1000","name":"Electrónica, Audio y Video"},{"id":"MCO175794","name":"Herramientas"},{"id":"MCO1574","name":"Hogar y Muebles"},{"id":"MCO1499","name":"Industrias y Oficinas"},{"id":"MCO1459","name":"Inmuebles"},{"id":"MCO1182","name":"Instrumentos Musicales"},{"id":"MCO1132","name":"Juegos y Juguetes"},{"id":"MCO3025","name":"Libros, Revistas y Comics"},{"id":"MCO1168","name":"Música, Películas y Series"},{"id":"MCO118204","name":"Recuerdos, Piñatería y Fiestas"},{"id":"MCO3937","name":"Relojes y Joyas"},{"id":"MCO1430","name":"Ropa y Accesorios"},{"id":"MCO180800","name":"Salud y Equipamiento Médico"},{"id":"MCO1540","name":"Servicios"},{"id":"MCO1953","name":"Otras categorías"}]
""".trimIndent()

val predictiveCategory = """
[
  {
    "domain_id": "MCO-HAND_DRAIN_PUMPS",
    "domain_name": "Bombas de achique manuales",
    "category_id": "MCO413656",
    "category_name": "Bombas Manuales",
    "attributes": [
    ]
  }
]
""".trimIndent()

val productsByCategory = """
{
  "site_id": "MCO",
  "paging": {},
  "results": [
    {
      "id": "MCO575637236",
      "site_id": "MCO",
      "title": "B",
      "seller": {},
      "price": 25000,
      "prices": null,
      "sale_price": null,
      "currency_id": "COP",
      "available_quantity": 1,
      "sold_quantity": 0,
      "buying_mode": "buy_it_now",
      "listing_type_id": "gold_pro",
      "stop_time": "2040-08-03T02:52:42.000Z",
      "condition": "new",
      "permalink": "https://articulo.mercadolibre.com.co/MCO-575637236-b-_JM",
      "thumbnail": "http://mco-s1-p.mlstatic.com/823124-MCO43084761228_082020-I.jpg",
      "accepts_mercadopago": true,
      "installments": {},
      "address": {},
      "shipping": {},
      "seller_address": {},
      "attributes": [],
      "differential_pricing": {},
      "original_price": null,
      "category_id": "MCO413656",
      "official_store_id": null,
      "domain_id": "MCO-HAND_DRAIN_PUMPS",
      "catalog_product_id": null,
      "tags": []
    },
    {
      "id": "MCO571586703",
      "site_id": "MCO",
      "title": "B&o Play De Bang & Olufsen A2",
      "seller": {},
      "price": 1850000,
      "prices": {
      },
      "sale_price": null,
      "currency_id": "COP",
      "available_quantity": 1,
      "sold_quantity": 0,
      "buying_mode": "buy_it_now",
      "listing_type_id": "gold_pro",
      "stop_time": "2040-07-13T04:00:00.000Z",
      "condition": "new",
      "permalink": "https://articulo.mercadolibre.com.co/MCO-571586703-bo-play-de-bang-olufsen-a2-_JM",
      "thumbnail": "http://mco-s2-p.mlstatic.com/894149-MCO42738104857_072020-I.jpg",
      "accepts_mercadopago": true,
      "installments": {},
      "address": {},
      "shipping": {},
      "seller_address": {},
      "attributes": [],
      "differential_pricing": {},
      "original_price": null,
      "category_id": "MCO413656",
      "official_store_id": null,
      "domain_id": "MCO-HAND_DRAIN_PUMPS",
      "catalog_product_id": null,
      "tags": []
    }
  ],
  "secondary_results": [
  ],
  "related_results": [
  ],
  "sort": {},
  "available_sorts": [],
  "filters": [],
  "available_filters": []
}
""".trimIndent()

val productDetailByCategory = """
{
  "id": "MCO571586703",
  "site_id": "MCO",
  "title": "B&o Play De Bang & Olufsen A2",
  "subtitle": null,
  "seller_id": 571244714,
  "category_id": "MCO413656",
  "official_store_id": null,
  "price": 1850000,
  "base_price": 1850000,
  "original_price": null,
  "currency_id": "COP",
  "initial_quantity": 12,
  "available_quantity": 1,
  "sold_quantity": 0,
  "sale_terms": [],
  "buying_mode": "buy_it_now",
  "listing_type_id": "gold_pro",
  "start_time": "2020-07-18T17:26:25.000Z",
  "stop_time": "2040-07-13T04:00:00.000Z",
  "condition": "new",
  "permalink": "https://articulo.mercadolibre.com.co/MCO-571586703-bo-play-de-bang-olufsen-a2-_JM",
  "thumbnail_id": "894149-MCO42738104857_072020",
  "thumbnail": "http://mco-s2-p.mlstatic.com/894149-MCO42738104857_072020-I.jpg",
  "secure_thumbnail": "https://mco-s2-p.mlstatic.com/894149-MCO42738104857_072020-I.jpg",
  "pictures": [
    {
          "id": "894149-MCO42738104857_072020",
          "url": "http://mco-s2-p.mlstatic.com/894149-MCO42738104857_072020-O.jpg",
          "secure_url": "https://mco-s2-p.mlstatic.com/894149-MCO42738104857_072020-O.jpg",
          "size": "500x394",
          "max_size": "1648x1299",
          "quality": ""
        },
        {
          "id": "955521-MCO42738104858_072020",
          "url": "http://mco-s2-p.mlstatic.com/955521-MCO42738104858_072020-O.jpg",
          "secure_url": "https://mco-s2-p.mlstatic.com/955521-MCO42738104858_072020-O.jpg",
          "size": "500x370",
          "max_size": "1500x1111",
          "quality": ""
        }
    ],
  "video_id": null,
  "descriptions": [],
  "accepts_mercadopago": true,
  "non_mercado_pago_payment_methods": [
  ],
  "shipping": {},
  "international_delivery_mode": "none",
  "seller_address": {},
  "seller_contact": null,
  "location": {
  },
  "geolocation": {},
  "coverage_areas": [
  ],
  "attributes": [],
  "warnings": [
  ],
  "listing_source": "",
  "variations": [
  ],
  "status": "active",
  "sub_status": [
  ],
  "tags": [],
  "warranty": "Garantía del vendedor: 10 días",
  "catalog_product_id": null,
  "domain_id": "MCO-HAND_DRAIN_PUMPS",
  "parent_item_id": null,
  "differential_pricing": null,
  "deal_ids": [
  ],
  "automatic_relist": false,
  "date_created": "2020-07-18T17:26:25.000Z",
  "last_updated": "2020-10-02T11:43:27.000Z",
  "health": 1,
  "catalog_listing": false
}
""".trimIndent()