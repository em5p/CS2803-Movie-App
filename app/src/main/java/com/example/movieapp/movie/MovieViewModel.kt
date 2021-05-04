package com.example.movieapp.movie

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.movie.MovieReviewDao
import com.example.movieapp.movie.MovieReviewEntity
import kotlinx.coroutines.launch
import java.util.*

class MovieViewModel(
        val database: MovieReviewDao,
        application: Application
): AndroidViewModel(application){

    fun getAllMovieReviews(): List<MovieReviewEntity> {
        var list: List<MovieReviewEntity> = arrayListOf<MovieReviewEntity>()
        viewModelScope.launch {
            list = database.getAllMovieReviews()
            Log.i("MovieViewModel", "all reviews list: " + list)
        }
        Log.i("MovieViewModel", "all reviews list outside: " + list)
        return list
    }

    fun getUserMovieReviews(username: String): List<MovieReviewEntity> {
        var list: List<MovieReviewEntity> = arrayListOf<MovieReviewEntity>()
        viewModelScope.launch {
            list = database.getUserMovieReviews(username)
            Log.i("MovieViewModel", "user reviews list: " + list)
        }
        Log.i("MovieViewModel", "user reviews list outside: " + list)
        return list
    }

    fun insert(movieReviewEntity: MovieReviewEntity): Long {
        var num: Long = 0
        viewModelScope.launch {
            num = database.insert(movieReviewEntity)
        }
        return num
    }

    fun getAllTitles(user: String): List<String> {
        var titles: List<String> = arrayListOf<String>()
        viewModelScope.launch {
            titles = database.getAllTitles(user)
            Log.i("MovieViewModel", "all movie titles: " + titles)
        }
        return titles
    }

    fun getMaxId(): Int {
        var max = 0
        viewModelScope.launch {
            max = database.getMaxID()
            Log.i("MovieViewModel", "max: " + max)
        }
        return max
    }
}