package com.example.movieapp

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["username", "password", "title", "rating", "provider", "review"],
    tableName = "reviewInfo")
data class MovieReviewEntity (
    @ColumnInfo(name = "username")
    val username: String = "",

    @ColumnInfo(name = "password")
    val password: String = "",

    @ColumnInfo(name = "title")
    val title: String = "",

    @ColumnInfo(name = "rating")
    val rating: Double = 0.0,

    @ColumnInfo(name = "provider")
    val provider: String = "",

    @ColumnInfo(name = "review")
    val review: String = "",

    @ColumnInfo(name = "curr_account")
    val currentAccount:Boolean = false,

    @ColumnInfo(name = "curr_movie")
    val currentMovieEntity:Boolean = true

)

