package com.example.movieapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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

        user = intent.getStringExtra("user").toString()
        Log.i("MovieListScreen", "user: " + user)
        val password = intent.getStringExtra("password")

        val movie_list = movieViewModel.getUserMovieReviews(user)
//        val movie_list = movieViewModel.getMovieReviews(user)
        MOVIES.addAll(movie_list)
        Log.i("movieListScreen", "Movies list: " + MOVIES.toString())

//        addMovieData()

        // creates a vertical linear layout manager
        rv_movies.layoutManager = LinearLayoutManager(this)

        //adapter with click listener
        rv_movies.adapter = MoviesAdapter(this, MOVIES) {
            // do something when clicked
                position ->
            Log.i("MovieListScreen", "movie clicked")
            val intent = Intent(this, MovieCardActivity::class.java)

            intent.putExtra("position", position)
            Log.i("MovieListScreen", "position: " + position)
            intent.putExtra("user", MOVIES[position].username)
            intent.putExtra("title", MOVIES[position].title)
            intent.putExtra("rating", MOVIES[position].rating)
            intent.putExtra("provider", MOVIES[position].provider)
            intent.putExtra("review", MOVIES[position].review)

            startActivity(intent)
        }

        add_button.setOnClickListener {
            val intent = Intent(this, NewMovieReview::class.java)
            startActivityForResult(intent, ADD_MOVIE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_MOVIE && resultCode == Activity.RESULT_OK && data != null) {

            val dataSource = MovieReviewDatabase.getInstance(this).movieReviewDao
            val movieViewModel = MovieViewModel(dataSource, this.application)

            val username = data.getStringExtra("user")
            val movieName = data.getStringExtra("title")
            val movieRating = data.getDoubleExtra("rating", 0.0)
            val movieProvider = data.getStringExtra("provider")
            val movieReview = data.getStringExtra("review")

            val max = movieViewModel.getMaxId()
            Log.i("MovieListScreen", "Max id: " + max)

            if (!username.isNullOrEmpty() && !movieName.isNullOrEmpty()
                && !movieProvider.isNullOrEmpty() && !movieReview.isNullOrEmpty()) {
                val movie = MovieReviewEntity(max + 1, username, movieName,
                    movieRating, movieProvider, movieReview)
                MOVIES.add(movie)
                Log.d("MovieListScreen", "after add movie list: " + MOVIES.toString())
                rv_movies.adapter?.notifyDataSetChanged()
                Log.d("MovieListScreen", "added new movie: " + movieName)
            }
        }
    }

    // add at least 5 sample entries to demonstrate functionality
//    private fun addMovieData() {
//        val movie1 = MovieReviewEntity(0, "username", "movie name",
//            0.0, "provider", "review")
//        val movie2 = MovieReviewEntity(1, "username2", "movie name2",
//            0.0, "provider2", "review2")
//        val movie3 = MovieReviewEntity(2, "username3",  "movie name3",
//            0.0, "provider3", "review3")
//        val movie4 = MovieReviewEntity(3, "username4",  "movie name4",
//            0.0, "provider4", "review4")
//        val movie5 = MovieReviewEntity(4, "username5", "movie name5",
//            0.0, "provider5", "review5")
//        val movie6 = MovieReviewEntity(5, "username6", "movie name6",
//            0.0, "provider6", "review6")
//        val movie7 = MovieReviewEntity(6, "username7", "movie name7",
//            0.0, "provider7", "review7")
//        val movie8 = MovieReviewEntity(7, "username8",  "movie name8",
//            0.0, "provider8", "review8")
//        val movie9 = MovieReviewEntity(8, "username9", "movie name9",
//            0.0, "provider9", "review9")
//        val movie10 = MovieReviewEntity(9, "username10","movie name10",
//            0.0, "provider10", "review10")
//
//
//        MOVIES.add(movie1)
//        MOVIES.add(movie2)
//        MOVIES.add(movie3)
//        MOVIES.add(movie4)
//        MOVIES.add(movie5)
//        MOVIES.add(movie6)
//        MOVIES.add(movie7)
//        MOVIES.add(movie8)
//        MOVIES.add(movie9)
//        MOVIES.add(movie10)
//    }
}