package com.example.movieapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieapp.MovieDatabaseDao
import com.example.movieapp.MovieReviewEntity


@Database(entities = [MovieReviewEntity::class], version = 1, exportSchema = true)
abstract class MovieReviewDatabase: RoomDatabase() {
    abstract val movieDatabaseDao: MovieDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: MovieReviewDatabase? = null

        fun getInstance(context: Context): MovieReviewDatabase {
            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieReviewDatabase::class.java,
                        "movieReview_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}