package com.example.movieapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.MovieListScreen.Companion.MOVIES
import kotlinx.android.synthetic.main.activity_edit_review.*
import com.example.movieapp.MovieListScreen.Companion.user
import com.example.movieapp.movie.MovieReviewDatabase
import com.example.movieapp.movie.MovieReviewEntity
import com.example.movieapp.movie.MovieViewModel

class EditReview: AppCompatActivity() {
    private val DONE_EDIT = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_review)

        val dataSource = MovieReviewDatabase.getInstance(this).movieReviewDao
        val movieViewModel = MovieViewModel(dataSource, this.application)

        movie_title.setText(intent.getStringExtra("title"))
        edit_rating.setText(intent.getDoubleExtra("rating", 0.0).toString())
        edit_review.setText(intent.getStringExtra("review"))
        edit_provider.setText(intent.getStringExtra("provider"))

        edit_done_button.setOnClickListener {
            val title = intent.getStringExtra("title")
            Log.i("EditReview", "title: " + title.toString())
            val rating = edit_rating.text.toString().toDouble()
            Log.i("EditReview", "rating: " + rating)
            val review = edit_review.text.toString()
            Log.i("EditReview", "review: " + review)
            val provider = edit_provider.text.toString()
            Log.i("EditReview", "provider: " + provider)
            if (!title.isNullOrEmpty() && review.isNotEmpty() && provider.isNotEmpty()) {
                Log.i("EditReview", "inside")
                val replaceReview = MovieReviewEntity(0, user, title, rating, provider, review)
                movieViewModel.updateReview(replaceReview)
                val size = MOVIES.size
                for (i in MOVIES.indices) {
                    if (replaceReview.username == MOVIES[i].username && replaceReview.title == MOVIES[i].title) {
                        Log.i("NewMovieReview", "removed movie: " + MOVIES[i].toString())
                        MOVIES.removeAt(i);
                        MOVIES.add(replaceReview)
                    }
                }
            }
            val doneEditIntent = Intent(this, MovieListScreen::class.java)
            doneEditIntent.putExtra("user", user)
            startActivityForResult(doneEditIntent, DONE_EDIT)
            onActivityResult(DONE_EDIT, Activity.RESULT_OK, doneEditIntent)
            finish()
        }

        val actionbar = supportActionBar
        // title of the page to edit review
        actionbar!!.title = "Edit Review"

        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}