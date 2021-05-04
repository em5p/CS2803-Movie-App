package com.example.movieapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.movie.MovieReviewEntity
import kotlinx.android.synthetic.main.activity_recycler_view.*
import kotlinx.android.synthetic.main.activity_search_results.*
//import com.example.movieapp.MovieListScreen.Companion.user
import com.example.movieapp.MovieListScreen.Companion.MOVIES

class SearchResults: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        search_results.layoutManager = LinearLayoutManager(this)

        val searchResults = intent.getStringArrayExtra("results")
        Log.i("SearchResults", "results: " + searchResults)
        val user = intent.getStringExtra("user")
        Log.i("SearchResults", "user: " + user)
        val searchList = arrayListOf<MovieReviewEntity>()
        var size = MOVIES.size - 1
        var exists = false
        var index = -1

        if (searchResults != null) {
            for (query in searchResults) {
                for (i in 0..size) {
                    if (MOVIES[i].title == query && MOVIES[i].username == user) {
                        exists = true
                        index = i
                    }
                }
                if (!exists && index != -1)
                    searchList.add(MOVIES[index])
            }

            Log.i("SearchResults", "searchResultEntityList: " + searchList.toString())
            val query = intent.getStringExtra("query")

            //adapter with click listener
            search_results.adapter = MoviesAdapter(this, searchList) { position ->
                Log.i("Search Results", "movie clicked")
                val intent = Intent(this, MovieCardActivity::class.java)

                intent.putExtra("position", position)
                Log.i("MovieListScreen", "position: " + position)
                intent.putExtra("user", searchList[position].username)
                intent.putExtra("title", searchList[position].title)
                intent.putExtra("rating", searchList[position].rating)
                intent.putExtra("provider", searchList[position].provider)
                intent.putExtra("review", searchList[position].review)

                startActivity(intent)
            }
        } else {
            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
            alertDialog.setTitle("No results")
            alertDialog.setMessage("No results for your search")
            alertDialog.setPositiveButton(
                    "Ok"
            ) { _, _ ->
                Toast.makeText(this, "Alert dialog closed.", Toast.LENGTH_LONG).show()
            }
            val alert: AlertDialog = alertDialog.create()
            alert.setCanceledOnTouchOutside(false)
            alert.show()
        }

        val actionbar = supportActionBar
        // title of the page of a specific movie review
        actionbar!!.title = "Search Results"

        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}