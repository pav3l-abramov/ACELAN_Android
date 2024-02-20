package com.example.acelanandroid.data

import androidx.room.TypeConverter

class FloatListConverter {
    @TypeConverter
    fun fromString(value: String): List<Float> {
        return value.split(",").map { it.toFloat() }
    }

    @TypeConverter
    fun toString(value: List<Float>): String {
        return value.joinToString(",")
    }
}