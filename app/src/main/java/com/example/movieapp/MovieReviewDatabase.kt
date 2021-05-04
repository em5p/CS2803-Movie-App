package com.example.movieapp

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MovieReviewEntity::class], version = 3, exportSchema = false)
abstract class MovieReviewDatabase : RoomDatabase() {

    abstract val movieReviewDao: MovieReviewDao

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
                            "movieReviewInfo"
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

