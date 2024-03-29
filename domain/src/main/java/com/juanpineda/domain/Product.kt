package com.juanpineda.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
        val id: String = "",
        val title: String = "",
        val price: Long = 0,
        val thumbnail: String = "",
        val categoryId: String = "",
        val availableQuantity: Int = 0,
        val pictures: List<String> = emptyList(),
        val status: String = "",
        val favorite: Boolean = false,
        val warranty: String = "",
        val currencyId: String = ""
) : Parcelable