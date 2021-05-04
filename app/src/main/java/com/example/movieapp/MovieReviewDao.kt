package com.example.movieapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.movieapp.MovieReviewEntity

@Dao
interface MovieReviewDao {

    @Insert
    suspend fun insert(movieReviewEntity: MovieReviewEntity)

    @Query("SELECT * FROM movieReviewInfo WHERE username LIKE :userName")
//    @Query("SELECT * FROM movieReviewInfo WHERE username IN (SELECT username FROM movieReviewInfo WHERE username LIKE 'hi') ")
    suspend fun getMovieReviews(userName: String): List<MovieReviewEntity>
//    suspend fun getMovieReviews(): List<MovieReviewEntity>

    @Query("INSERT INTO movieReviewInfo (username, title, rating, provider, review) VALUES (:username_, :title_, :rating_, :provider_, :review_)")
    suspend fun insertMovieReview(username_: String, title_: String, rating_: Double, provider_: String, review_: String)

    @Query("SELECT MAX(reviewID) FROM movieReviewInfo")
    suspend fun getMaxID(): Int
}