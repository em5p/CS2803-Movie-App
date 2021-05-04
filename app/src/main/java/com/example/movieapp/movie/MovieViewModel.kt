package com.example.movieapp.movie

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.MovieListScreen
import kotlinx.coroutines.launch
import com.example.movieapp.MovieListScreen.Companion.MOVIES
import java.util.*
import kotlin.collections.ArrayList

class MovieViewModel(
        val database: MovieReviewDao,
        application: Application
): AndroidViewModel(application){

    fun getAllMovieReviews(): List<MovieReviewEntity> {
        var list: List<MovieReviewEntity> = arrayListOf<MovieReviewEntity>()
        viewModelScope.launch {
            list = database.getAllMovieReviews()
            var size = MOVIES.size
            var exists = false
            for (movie in list) {
                for (i in 0..size-1) {
                    if (MOVIES[i].title == movie.title && MOVIES[i].username == movie.username)
                        exists = true
                }
                if (!exists)
                    MOVIES.add(movie)
            }
            Log.i("MovieViewModel", "all reviews list size ${list.size}: " + list)
        }
        return list
    }

    fun getUserMovieReviews(username: String): List<MovieReviewEntity> {
        var list: List<MovieReviewEntity> = arrayListOf<MovieReviewEntity>()
        viewModelScope.launch {
            list = database.getUserMovieReviews(username)
            Log.i("MovieViewModel", "user reviews list: " + list)
        }
        return list
    }

    fun getInitialList(username: String): ArrayList<MovieReviewEntity> {
        var list: List<MovieReviewEntity> = arrayListOf<MovieReviewEntity>()
        viewModelScope.launch {
            list = database.getUserMovieReviews(username)
            var size = MOVIES.size
            var exists = false
            for (movie in list) {
                for (i in 0..size-1) {
                    if (MOVIES[i].title == movie.title && MOVIES[i].username == movie.username)
                        exists = true
                }
                if (!exists)
                    MOVIES.add(movie)
            }
//            MOVIES.addAll(list)
            Log.i("MovieViewModel", "Initial list size ${MOVIES.size}: " + MOVIES.toString())
        }
        return MOVIES
    }

    fun insert(movieReviewEntity: MovieReviewEntity): Long {
        var num: Long = 0
        viewModelScope.launch {
            num = database.insert(movieReviewEntity)
            Log.i("MovieViewModel", "movie added! " + movieReviewEntity.title)
        }
        return num
    }

    fun updateReview(reviewEntity: MovieReviewEntity) {
        var username = reviewEntity.username
        var title = reviewEntity.title
        var rating = reviewEntity.title
        var provider = reviewEntity.provider
        var review = reviewEntity.provider
        viewModelScope.launch {
            database.updateReview(rating, provider, review, username, title)
            Log.i("MovieViewModel", "movie updated! " + reviewEntity.title)
        }
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