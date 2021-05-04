package com.example.movieapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieReviewInfo")
data class MovieReviewEntity (
    @PrimaryKey(autoGenerate = true)
    var reviewID: Int = 0,

    @ColumnInfo(name = "username")
    val username: String = "",

    @ColumnInfo(name = "title")
    val title: String = "",

    @ColumnInfo(name = "rating")
    val rating: Double = 0.0,

    @ColumnInfo(name = "provider")
    val provider: String = "",

    @ColumnInfo(name = "review")
    val review: String = ""
)

