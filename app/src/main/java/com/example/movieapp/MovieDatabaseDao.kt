package com.example.movieapp

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movieapp.MovieReviewEntity

@Dao
interface MovieDatabaseDao {

    @Query("SELECT * FROM REVIEWINFO WHERE username == :username AND title == :title AND review == :review")
    suspend fun containsMovieReview(username: String, title: String, review: String): MovieReviewEntity?

    @Query("SELECT * FROM REVIEWINFO WHERE username == :username ORDER BY rating DESC")
    fun getAllMovieReviews(username: String): LiveData<List<MovieReviewEntity>>

    @Insert
    suspend fun insert(movieReview: MovieReviewEntity)

    @Query("SELECT * FROM reviewInfo WHERE username == :username LIMIT 1")
    suspend fun accountExists(username: String): List<MovieReviewEntity>

    @Query("SELECT * FROM reviewInfo WHERE username == :username AND password == :password")
    suspend fun validLogin(username: String, password: String): List<MovieReviewEntity>

    @Query("SELECT * FROM reviewInfo WHERE curr_account == :currAcount")
    fun getCurrentUser(currAcount:Boolean = true): MovieReviewEntity

    //Returns the clicked movie
    @Query("SELECT * FROM reviewInfo WHERE curr_movie == :currMovie")
    fun returnClickedMovie(currMovie:Boolean = true): MovieReviewEntity

    @Query("SELECT * FROM reviewInfo")
    fun getAll(): List<MovieReviewEntity>
}