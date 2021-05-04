package com.example.movieapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
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

        edit_rating.setText(intent.getDoubleExtra("rating", 0.0).toString())
        edit_review.setText(intent.getStringExtra("review"))
        edit_provider.setText(intent.getStringExtra("provider"))

        edit_done_button.setOnClickListener {
            val title = intent.getStringExtra("title")
            val rating = edit_rating.text.toString().toDouble()
            val review = edit_review.text.toString()
            val provider = edit_provider.text.toString()
            if (title.isNullOrEmpty() && review.isEmpty() && provider.isNotEmpty()) {
                val replaceReview = title?.let { it1 -> MovieReviewEntity(0, user, it1, rating, provider, review) }
                if (replaceReview != null) {
                    movieViewModel.insert(replaceReview)
                }
            }

            val doneEditIntent = Intent(this, MovieListScreen::class.java)
            startActivityForResult(doneEditIntent, DONE_EDIT)
            onActivityResult(DONE_EDIT, Activity.RESULT_OK, doneEditIntent)
            finish()
        }

        val actionbar = supportActionBar
        // title of the page to edit trip
        actionbar!!.title = "Edit Trip"

        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}