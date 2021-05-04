//package com.example.movieapp.movie
//
//import com.example.movieapp.database.RegisterDatabaseDao
//import com.example.movieapp.database.RegisterEntity
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.launch
//
//class MovieRepository(private val dao: MovieReviewDao) {
//
////    suspend fun insert(review: MovieReviewEntity) {
////        return dao.insert(review)
////    }
//
//    suspend fun getMovieReviews(): List<MovieReviewEntity> {
//        return dao.getMovieReviews()
//    }
//
//    suspend fun insertMovieReview(movieReviewEntity: MovieReviewEntity) {
//        val user = movieReviewEntity.username
//        val title = movieReviewEntity.title
//        val rating = movieReviewEntity.rating
//        val provider = movieReviewEntity.provider
//        val review = movieReviewEntity.review
//
//        dao.insertMovieReview(0, user, title, rating, provider, review)
//
//    }
//
//    suspend fun getMaxId(): Int {
//        return dao.getMaxID()
//    }
//
//
//
//}