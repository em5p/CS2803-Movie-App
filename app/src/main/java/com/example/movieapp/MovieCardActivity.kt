package com.example.movieapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_movie_card.*

class MovieCardActivity: AppCompatActivity() {
    private val EDIT_CODE = 3
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_card)

        val actionbar = supportActionBar
        // title of the page of a specific movie review
        actionbar!!.title = "Your Movie Review"

        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        // set review info
        movie_title.text = intent.getStringExtra("title")
        movie_rating.text = intent.getDoubleExtra("rating", 0.0).toString()
        movie_provider.text = intent.getStringExtra("provider")
        movie_review.text = intent.getStringExtra("review")


        edit_button.setOnClickListener {
            Log.d("MovieCardActivity", "Edit button pressed")

            val editIntent = Intent(this, EditReview::class.java)
            editIntent.putExtra("title", movie_title.text.toString())
            editIntent.putExtra("rating", movie_rating.text.toString())
            editIntent.putExtra("provider", movie_provider.text.toString())
            editIntent.putExtra("review", movie_provider.text.toString())
            startActivityForResult(editIntent, EDIT_CODE)
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}