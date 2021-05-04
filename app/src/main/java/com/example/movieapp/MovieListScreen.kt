package com.example.movieapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.movie.MovieReviewDao
import com.example.movieapp.movie.MovieReviewDatabase
import com.example.movieapp.movie.MovieReviewEntity
import com.example.movieapp.movie.MovieViewModel
import kotlinx.android.synthetic.main.activity_recycler_view.*


class MovieListScreen : AppCompatActivity() {
    val ADD_MOVIE = 1

    companion object {
        var MOVIES: ArrayList<MovieReviewEntity> = ArrayList<MovieReviewEntity>()
        lateinit var user: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val dataSource = MovieReviewDatabase.getInstance(this).movieReviewDao
        val movieViewModel = MovieViewModel(dataSource, this.application)
        var userMovieList: ArrayList<MovieReviewEntity> = arrayListOf()

        user = intent.getStringExtra("user").toString()
        Log.i("MovieListScreen", "user: " + user)

//        val password = intent.getStringExtra("password")

        val movie1 = MovieReviewEntity(0, "d", "ladybird", 4.3, "netflix", "good watch")
        val movie2 = MovieReviewEntity(0, "s", "inside out", 3.7, "disney", "good")
        val movie3 = MovieReviewEntity(0, "p", "grave of the fireflies", 4.5, "online", "good")
        val movie4 = MovieReviewEntity(0, "r", "spirited away", 5.0, "online", "good")
        val movie5 = MovieReviewEntity(0, "p", "parasite", 4.2, "netflix", "deep")

        movieViewModel.insert(movie1)
        movieViewModel.insert(movie2)
        movieViewModel.insert(movie3)
        movieViewModel.insert(movie4)
        movieViewModel.insert(movie5)

        var m1 = false
        var m2 = false
        var m3 = false
        var m4 = false
        var m5 = false
        for (movie in MOVIES) {
            if (movie.title == movie1.title && movie.username == movie1.username) {
                m1 = true
            }
            if (movie.title == movie2.title && movie.username == movie2.username) {
                m2 = true
            }
            if (movie.title == movie3.title && movie.username == movie3.username) {
                m3 = true
            }
            if (movie.title == movie4.title && movie.username == movie4.username) {
                m4 = true
            }
            if (movie.title == movie5.title && movie.username == movie5.username) {
                m5 = true
            }
        }
        if (!m1) MOVIES.add(movie1)
        if (!m2) MOVIES.add(movie2)
        if (!m3) MOVIES.add(movie3)
        if (!m4) MOVIES.add(movie4)
        if (!m5) MOVIES.add(movie5)



//        addMovieData()
        movieViewModel.getAllMovieReviews()
//        val movie_list = movieViewModel.getUserMovieReviews(user)
//        Log.i("movieListScreen", "onCreate movie_list: " + movie_list.toString())
////        val movie_list = movieViewModel.getMovieReviews(user)
//        MOVIES.addAll(movie_list)
//        Log.i("movieListScreen", "MOVIES list oncreate: " + MOVIES.toString())

        MOVIES = movieViewModel.getInitialList(user)

        for (movie in MOVIES) {
            if (movie.username == user) {
                userMovieList.add(movie)
            }
        }

        Log.i("movieListScreen", "MOVIES list oncreate: " + MOVIES.toString())
        Log.i("movieListScreen", "user movie list oncreate: " + userMovieList.toString())

        // creates a vertical linear layout manager
        rv_movies.layoutManager = LinearLayoutManager(this)

        //adapter with click listener
//        rv_movies.adapter = MoviesAdapter(this, MOVIES) {
        rv_movies.adapter = MoviesAdapter(this, userMovieList) {
            // do something when clicked
                position ->
            Log.i("MovieListScreen", "movie clicked")
            val intent = Intent(this, MovieCardActivity::class.java)

            intent.putExtra("position", position)
            Log.i("MovieListScreen", "position: " + position)
            intent.putExtra("user", userMovieList[position].username)
            intent.putExtra("title", userMovieList[position].title)
            intent.putExtra("rating", userMovieList[position].rating)
            intent.putExtra("provider", userMovieList[position].provider)
            intent.putExtra("review", userMovieList[position].review)

            startActivity(intent)
        }

        search_bar.isSubmitButtonEnabled = false
        search_bar.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val allList = movieViewModel.getAllTitles(user)
                Log.i("MovieListScreen", "all movie titles: " + allList)
                if (allList.contains(query)) {
                    Log.i("MovieListScreen", "found movie!")
                } else {
                    Toast.makeText(this@MovieListScreen, "No Match found", Toast.LENGTH_LONG).show()
                }
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        add_button.setOnClickListener {
            val intent = Intent(this, NewMovieReview::class.java)
            startActivityForResult(intent, ADD_MOVIE)
        }
    }


    // add at least 5 sample entries to demonstrate functionality
//    private fun addMovieData() {
//
//        val movie1 = MovieReviewEntity(0, "d", "ladybird", 4.3, "netflix", "good watch")
//        val movie2 = MovieReviewEntity(0, "s", "inside out", 3.7, "disney", "good")
//        val movie3 = MovieReviewEntity(0, "p", "grave of the fireflies", 4.5, "online", "good")
//        val movie4 = MovieReviewEntity(0, "r", "spirited away", 5.0, "online", "good")
//        val movie5 = MovieReviewEntity(0, "p", "parasite", 4.2, "netflix", "deep")
//
//        var m1 = false
//        var m2 = false
//        var m3 = false
//        var m4 = false
//        var m5 = false
//        for (movie in MOVIES) {
//            if (movie.title == movie1.title && movie.username == movie1.username) {
//                m1 = true
//            }
//            if (movie.title == movie2.title && movie.username == movie2.username) {
//                m2 = true
//            }
//            if (movie.title == movie3.title && movie.username == movie3.username) {
//                m3 = true
//            }
//            if (movie.title == movie4.title && movie.username == movie4.username) {
//                m4 = true
//            }
//            if (movie.title == movie5.title && movie.username == movie5.username) {
//                m5 = true
//            }
//        }
//        if (!m1) MOVIES.add(movie1)
//        if (!m2) MOVIES.add(movie2)
//        if (!m3) MOVIES.add(movie3)
//        if (!m4) MOVIES.add(movie4)
//        if (!m5) MOVIES.add(movie5)
//    }
}