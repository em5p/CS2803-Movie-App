package com.example.movieapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recycler_view.*
import kotlinx.android.synthetic.main.review_list_item.*


class MovieListScreen : AppCompatActivity() {
    val ADD_MOVIE = 1

    companion object {
        val MOVIES: ArrayList<MovieReviewEntity> = ArrayList<MovieReviewEntity>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)


        addMovieData()

        // creates a vertical linear layout manager
        rv_movies.layoutManager = LinearLayoutManager(this)

        //adapter with click listener
        rv_movies.adapter = MoviesAdapter(this, MOVIES) {
            // do something when clicked
                position ->
            Log.i("MovieListScreen", "movie clicked")
            val intent = Intent(this,MovieCardActivity::class.java)

            intent.putExtra("position", position)
            Log.i("MovieListScreen", "position: " + position)
            intent.putExtra("username", MOVIES[position].username)
            intent.putExtra("password", MOVIES[position].password)
            intent.putExtra("movie name", MOVIES[position].title)
            intent.putExtra("movie rating", MOVIES[position].rating)
            intent.putExtra("movie provider", MOVIES[position].provider)
            intent.putExtra("movie review", MOVIES[position].review)

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
            val username = data.getStringExtra("username")
            val password = data.getStringExtra("password")
            val movieName = data.getStringExtra("movie name")
            val movieRating = data.getDoubleExtra("movie rating", 0.0)
            val movieProvider = data.getStringExtra("movie provider")
            val movieReview = data.getStringExtra("movie review")
            if (!username.isNullOrEmpty() && !password.isNullOrEmpty() && !movieName.isNullOrEmpty()
                && !movieProvider.isNullOrEmpty() && !movieReview.isNullOrEmpty()) {
                val movie = MovieReviewEntity(username, password, movieName,
                    movieRating, movieProvider, movieReview)
                MOVIES.add(movie)
                rv_movies.adapter?.notifyDataSetChanged()
                Log.d("MainActivity", "added new trip: " + movieName)
            }
        }
    }

    // add at least 5 sample entries to demonstrate functionality
    private fun addMovieData() {
        val movie1 = MovieReviewEntity("username", "password", "movie name",
            0.0, "provider", "review")
        val movie2 = MovieReviewEntity("username2", "password2", "movie name2",
            0.0, "provider2", "review2")
        val movie3 = MovieReviewEntity("username3", "password3", "movie name3",
            0.0, "provider3", "review3")
        val movie4 = MovieReviewEntity("username4", "password4", "movie name4",
            0.0, "provider4", "review4")
        val movie5 = MovieReviewEntity("username5", "password5", "movie name5",
            0.0, "provider5", "review5")
        val movie6 = MovieReviewEntity("username6", "password6", "movie name6",
            0.0, "provider6", "review6")
        val movie7 = MovieReviewEntity("username7", "password7", "movie name7",
            0.0, "provider7", "review7")
        val movie8 = MovieReviewEntity("username8", "password8", "movie name8",
            0.0, "provider8", "review8")
        val movie9 = MovieReviewEntity("username9", "password9", "movie name9",
            0.0, "provider9", "review9")
        val movie10 = MovieReviewEntity("username10", "password10", "movie name10",
            0.0, "provider10", "review10")


        MOVIES.add(movie1)
        MOVIES.add(movie2)
        MOVIES.add(movie3)
        MOVIES.add(movie4)
        MOVIES.add(movie5)
        MOVIES.add(movie6)
        MOVIES.add(movie7)
        MOVIES.add(movie8)
        MOVIES.add(movie9)
        MOVIES.add(movie10)
    }
}