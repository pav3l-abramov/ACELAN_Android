package com.example.acelanandroid.room

import androidx.room.TypeConverter

class FloatListConverter {
    @TypeConverter
    fun fromString(string: String?): ArrayList<Float> {
        return ArrayList(string?.split(";")?.mapNotNull { it.toFloatOrNull() } ?: emptyList())
    }

    @TypeConverter
    fun toString(value: List<Float>?): String {
        return value?.joinToString(";"){ it.toString() } ?: ""
    }
}