package com.example.movieapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.movie.MovieReviewDatabase
import com.example.movieapp.movie.MovieReviewEntity
import com.example.movieapp.movie.MovieViewModel
import kotlinx.android.synthetic.main.activity_recycler_view.*
import kotlinx.android.synthetic.main.activity_search_results.*
import java.util.*
import kotlin.collections.ArrayList


class MovieListScreen : AppCompatActivity() {
    val ADD_MOVIE = 1
    var userMovieList: ArrayList<MovieReviewEntity> = arrayListOf()
    var displayList: ArrayList<MovieReviewEntity> = arrayListOf()

    companion object {
        var MOVIES: ArrayList<MovieReviewEntity> = ArrayList<MovieReviewEntity>()
        lateinit var user: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val dataSource = MovieReviewDatabase.getInstance(this).movieReviewDao
        val movieViewModel = MovieViewModel(dataSource, this.application)


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
        displayList.addAll(userMovieList)

        Log.i("movieListScreen", "MOVIES list oncreate: " + MOVIES.toString())
        Log.i("movieListScreen", "user movie list oncreate: " + userMovieList.toString())

        // creates a vertical linear layout manager
        rv_movies.layoutManager = LinearLayoutManager(this)

        //adapter with click listener
//        rv_movies.adapter = MoviesAdapter(this, MOVIES) {
        Log.i("MovieListScreen", "display list: " + displayList)
        rv_movies.adapter = MoviesAdapter(this, displayList) {
            // do something when clicked
                position ->
            Log.i("MovieListScreen", "movie clicked")
            val intent = Intent(this, MovieCardActivity::class.java)

            intent.putExtra("position", position)
            Log.i("MovieListScreen", "position: " + position)
            intent.putExtra("user", displayList[position].username)
            intent.putExtra("title", displayList[position].title)
            intent.putExtra("rating", displayList[position].rating)
            intent.putExtra("provider", displayList[position].provider)
            intent.putExtra("review", displayList[position].review)

            startActivity(intent)
        }
        add_button.setOnClickListener {
            val intent = Intent(this, NewMovieReview::class.java)
            startActivityForResult(intent, ADD_MOVIE)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuItem = menu!!.findItem(R.id.search)

        if (menuItem != null) {
            val searchView = menuItem.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty()) {
                        displayList.clear()
                        val search = newText.toLowerCase(Locale.getDefault())
                        userMovieList.forEach {
                            if (it.title.toLowerCase(Locale.getDefault()).contains(search)) {
                                displayList.add(it)
                            }
                        }
                        rv_movies.adapter!!.notifyDataSetChanged()
                    } else {
                        displayList.clear()
                        displayList.addAll(userMovieList)
                        rv_movies.adapter!!.notifyDataSetChanged()
                    }
                    return true
                }

            })
        }
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}