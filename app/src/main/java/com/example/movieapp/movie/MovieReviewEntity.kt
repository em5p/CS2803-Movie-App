package com.example.movieapp.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["username", "title"], unique = true)], tableName = "movieReviewInfo")
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

