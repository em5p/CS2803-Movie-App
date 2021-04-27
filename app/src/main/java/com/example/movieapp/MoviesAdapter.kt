package com.example.movieapp

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.review_list_item.view.*

class MoviesAdapter(
    var activity: Activity,
    var movieList: ArrayList<MovieReviewEntity>,
    var getPosition: (Int) -> Unit): RecyclerView.Adapter<MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder =
        MoviesViewHolder(
            LayoutInflater.from(activity).inflate(R.layout.review_list_item, parent, false)
        )

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(movieList[position], position, getPosition)
    }

    override fun getItemCount() = movieList.size

    interface onReviewClickListener {
        fun onReviewClicked(movieReviewEntity: MovieReviewEntity)
    }
}

class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var movieName: TextView = itemView.movie_name
    var movieRating: TextView = itemView.movie_rating
    var view: View = itemView

    // for binding contact info to view
    fun bind(item: MovieReviewEntity, position: Int, getPosition: (Int) -> Unit) {
        movieName.text = item.title
        movieRating.text = "Rating: " + item.rating

        // sets up the onClickListener to the entire view
        view.setOnClickListener{
            getPosition(position)
        }
    }
}
