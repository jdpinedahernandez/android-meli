package com.juanpineda.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun listToString(value: List<String>?) = Gson().toJson(value)

    @TypeConverter
    fun stringToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
}