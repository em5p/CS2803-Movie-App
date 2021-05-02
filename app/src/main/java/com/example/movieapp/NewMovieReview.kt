package com.example.movieapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add.*

class NewMovieReview: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val actionbar = supportActionBar
        // title of the page to add reviews
        actionbar!!.title = "Add a movie review"

        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        add_done_button.setOnClickListener {
            val doneIntent = Intent(this, MovieListScreen::class.java)
            doneIntent.putExtra("title", title_input.text.toString())
            doneIntent.putExtra("rank", rank_input.text.toString())
            doneIntent.putExtra("provider", provider_input.text.toString())
            doneIntent.putExtra("review", review_input.text.toString())

            // add to database??

            startActivity(doneIntent)
            finish()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}