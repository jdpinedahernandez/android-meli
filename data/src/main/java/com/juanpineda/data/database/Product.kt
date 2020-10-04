package com.juanpineda.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = false) val id: String,
    val title: String,
    val price: Long,
    val thumbnail: String,
    val categoryId: String,
    val availableQuantity: Int,
    val pictures: List<String>,
    val status: String,
    val favorite: Boolean,
    val warranty: String,
    val currencyId: String
)