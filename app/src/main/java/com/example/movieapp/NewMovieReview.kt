package com.example.movieapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add.*
import com.example.movieapp.MovieListScreen.Companion.user
import com.example.movieapp.movie.MovieReviewDatabase
import com.example.movieapp.movie.MovieReviewEntity
import com.example.movieapp.movie.MovieViewModel

class NewMovieReview: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val dataSource = MovieReviewDatabase.getInstance(this).movieReviewDao
        val movieViewModel = MovieViewModel(dataSource, this.application)

        Log.i("NewMovieReview", "curr user: " + user)

        val actionbar = supportActionBar
        // title of the page to add reviews
        actionbar!!.title = "Add a movie review"

        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        add_done_button.setOnClickListener {
            val doneIntent = Intent(this, MovieListScreen::class.java)
            doneIntent.putExtra("title", title_input.text.toString())
            doneIntent.putExtra("rating", rating_input.text.toString())
            doneIntent.putExtra("provider", provider_input.text.toString())
            doneIntent.putExtra("review", review_input.text.toString())
            doneIntent.putExtra("user", user)

            val max = movieViewModel.getMaxId()
            Log.i("NewMovieReview", "Max id: " + max)

            val newReview = MovieReviewEntity(max + 1, user, title_input.text.toString(),
                    rating_input.text.toString().toDouble(), provider_input.text.toString(),
                    review_input.text.toString())
            Log.i("NewMovieReview", "new review: " + newReview.toString())
//            movieViewModel.insertMovieReview(newReview)
            movieViewModel.insert(newReview)
            Log.i("NewMovieReview", "all movie list: " + movieViewModel.getAllMovieReviews())
            Log.i("NewMovieReview", "user movie list: " + movieViewModel.getUserMovieReviews(user))
            // add to database??

            startActivityForResult(doneIntent, 1)
//            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}