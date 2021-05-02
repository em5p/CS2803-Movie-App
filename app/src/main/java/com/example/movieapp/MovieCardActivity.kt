package com.example.movieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_movie_card.*

class MovieCardActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_card)

        val actionbar = supportActionBar
        // title of the page of a specific movie review
        actionbar!!.title = "Your Movie Review"

        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        // set review info
        movie_name.text = intent.getStringExtra("movie name")
        movie_rating.text = intent.getDoubleExtra("movie rating", 0.0).toString()
        movie_provider.text = intent.getStringExtra("movie provider")
        movie_review.text = intent.getStringExtra("movie review")


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}