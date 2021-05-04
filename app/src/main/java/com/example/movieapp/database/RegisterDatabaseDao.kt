package com.example.movieapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.movieapp.MovieReviewEntity


@Dao
interface RegisterDatabaseDao {

    @Insert
    suspend fun insert(register: RegisterEntity)

    //@Delete
    //suspend  fun deleteSubscriber(register: RegisterEntity):Int

    @Query("SELECT * FROM Register_users_table ORDER BY userId DESC")
    fun getAllUsers(): LiveData<List<RegisterEntity>>

    @Query("DELETE FROM Register_users_table")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM Register_users_table WHERE user_name LIKE :userName")
    suspend fun getUserInfo(userName: String): RegisterEntity?

//    @Query("SELECT * FROM movieReviewInfo WHERE username LIKE :userName")
//    suspend fun getMovieReviews(userName: String): ArrayList<MovieReviewEntity>
//
//    @Query("INSERT INTO movieReviewInfo (username, title, rating, provider, review) VALUES (:username_, :title_, :rating_, :provider_, :review_)")
//    suspend fun insertMovieReview(username_: String, title_: String, rating_: Double, provider_: String, review_: String)

}