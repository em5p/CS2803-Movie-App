package com.example.movieapp.database

import androidx.room.TypeConverter

class MovieConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromString(stringListString: String): List<String> {
            return stringListString.split(",").map { it }
        }

        @TypeConverter
        @JvmStatic
        fun toString(stringList: List<String>): String {
            return stringList.joinToString(separator = ",")
        }
    }
}