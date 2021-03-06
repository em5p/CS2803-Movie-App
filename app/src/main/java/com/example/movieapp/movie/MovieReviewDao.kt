package com.example.movieapp.movie

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieReviewEntity: MovieReviewEntity): Long


     @Query("SELECT * FROM movieReviewInfo")
     suspend fun getAllMovieReviews(): List<MovieReviewEntity>

     @Query("SELECT * FROM movieReviewInfo WHERE username LIKE :userName ORDER BY rating DESC")
     suspend fun getUserMovieReviews(userName: String): List<MovieReviewEntity>
//    @Query("SELECT * FROM movieReviewInfo WHERE username IN (SELECT username FROM movieReviewInfo WHERE username LIKE 'hi') ")
//    suspend fun getMovieReviews(userName: String): List<MovieReviewEntity>
//    suspend fun getMovieReviews(): List<MovieReviewEntity>

    @Query("SELECT title FROM movieReviewInfo WHERE username = :user")
    suspend fun getAllTitles(user: String): List<String>

    @Query("SELECT * FROM movieReviewInfo WHERE username = :user AND title = :title_")
    suspend fun getSpecificMovie(user: String, title_: String): MovieReviewEntity


    @Query("UPDATE movieReviewInfo SET rating = :newRating, provider = :newProvider, review = :newReview WHERE username = :user AND title = :title_")
    suspend fun updateReview(newRating: String, newProvider: String, newReview: String, user: String, title_: String)

    @Query("SELECT MAX(reviewID) FROM movieReviewInfo")
    suspend fun getMaxID(): Int

}