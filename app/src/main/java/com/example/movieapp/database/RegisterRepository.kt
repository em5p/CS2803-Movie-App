package com.example.movieapp.database

import android.util.Log
import androidx.lifecycle.LiveData

class RegisterRepository(private val dao: RegisterDatabaseDao) {

    val users = dao.getAllUsers()
    suspend fun insert(user: RegisterEntity) {
        return dao.insert(user)
    }

    suspend fun getUserName(userName: String):RegisterEntity?{
        Log.i("RegisterRepository", "inside Repository Get users function")
        return dao.getUsername(userName)
    }

}