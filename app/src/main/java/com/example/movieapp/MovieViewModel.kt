package com.example.movieapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.database.RegisterDatabaseDao
import kotlinx.coroutines.launch

class MovieViewModel(
        val database: MovieReviewDao,
        application: Application
): AndroidViewModel(application) {

    fun getMovieReviews(userName: String): List<MovieReviewEntity> {
        var list: List<MovieReviewEntity> = arrayListOf<MovieReviewEntity>()
        viewModelScope.launch {
            list = database.getMovieReviews(userName)
        }
        return list
    }

    fun insertMovieReview(movieReviewEntity: MovieReviewEntity) {
        val user = movieReviewEntity.username
        val title = movieReviewEntity.title
        val rating = movieReviewEntity.rating
        val provider = movieReviewEntity.provider
        val review = movieReviewEntity.review
        viewModelScope.launch {
            database.insertMovieReview(user, title, rating, provider, review)
        }
    }

    fun getMaxId(): Int {
        var max = 0
        viewModelScope.launch {
            max = database.getMaxID()
        }
        return max
    }
}