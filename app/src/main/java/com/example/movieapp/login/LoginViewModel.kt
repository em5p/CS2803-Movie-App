package com.example.movieapp.login

import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movieapp.database.RegisterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: RegisterRepository, application: Application) :
    AndroidViewModel(application), Observable {

    val users = repository.users

    @Bindable
    val inputUsername = MutableLiveData<String>()

    @Bindable
    val inputPassword = MutableLiveData<String>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigatetoRegister = MutableLiveData<Boolean>()

    val navigatetoRegister: LiveData<Boolean>
        get() = _navigatetoRegister

    private val _navigatetoUserDetails = MutableLiveData<Boolean>()

    val navigatetoUserDetails: LiveData<Boolean>
        get() = _navigatetoUserDetails

    private val _navigatetoMovieListScreen = MutableLiveData<Boolean>()

    val navigatetoMovieListScreen: LiveData<Boolean>
        get() = _navigatetoMovieListScreen

    private val _errorToast = MutableLiveData<Boolean>()

    val errorToast: LiveData<Boolean>
        get() = _errorToast

    private val _errorToastUsername = MutableLiveData<Boolean>()

    val errorToastUsername: LiveData<Boolean>
        get() = _errorToastUsername

    private val _errorToastInvalidPassword = MutableLiveData<Boolean>()

    val errorToastInvalidPassword: LiveData<Boolean>
        get() = _errorToastInvalidPassword


    fun signUp() {
        _navigatetoRegister.value = true
    }

    fun loginButton() {
        Log.i("LoginViewModel", "Username: " + inputUsername.value)
        Log.i("LoginViewModel", "Password: " + inputPassword.value)
        if (inputUsername.value == null || inputPassword.value == null) {
            _errorToast.value = true
        } else {
            uiScope.launch {
                val usersNames = repository.getUserName(inputUsername.value!!)
                if (usersNames != null) {
                    if(usersNames.passwrd == inputPassword.value){
                        Log.i("LoginViewModel", "Successful Login!")
                        inputUsername.value = null
                        inputPassword.value = null
                        _navigatetoMovieListScreen.value = true
//                      _navigatetoUserDetails.value = true
                    }else{
                        _errorToastInvalidPassword.value = true
                    }
                } else {
                    _errorToastUsername.value = true
                }
            }
        }
    }




    fun doneNavigatingRegister() {
        _navigatetoRegister.value = false
    }

    fun doneNavigatingUserDetails() {
        _navigatetoUserDetails.value = false
    }

    fun doneNavigatingToMovieListScreen() {
        _navigatetoMovieListScreen.value = false
    }


    fun donetoast() {
        _errorToast.value = false
        Log.i("LoginViewModel", "Invalid user/pass combo: Done toasting ")
    }


    fun donetoastErrorUsername() {
        _errorToastUsername .value = false
        Log.i("LoginViewModel", "Invalid username: Done toasting ")
    }

    fun donetoastInvalidPassword() {
        _errorToastInvalidPassword .value = false
        Log.i("LoginViewModel", "Invalid password: Done toasting ")
    }



    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }


}