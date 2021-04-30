package com.example.movieapp.register

import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.example.movieapp.database.RegisterEntity
import com.example.movieapp.database.RegisterRepository
import kotlinx.coroutines.*


class RegisterViewModel(private val repository: RegisterRepository, application: Application) :
    AndroidViewModel(application), Observable {

    init {
        Log.i("RegisterViewModel", "init Register View model")
    }


    private var userdata: String? = null

    var userDetailsLiveData = MutableLiveData<Array<RegisterEntity>>()

    @Bindable
    val inputFirstName = MutableLiveData<String>()

    @Bindable
    val inputLastName = MutableLiveData<String>()

    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val inputUsername = MutableLiveData<String>()

    @Bindable
    val inputPassword = MutableLiveData<String>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private val _navigateto = MutableLiveData<Boolean>()

    val navigateto: LiveData<Boolean>
        get() = _navigateto

    private val _errorToast = MutableLiveData<Boolean>()

    val errorToast: LiveData<Boolean>
        get() = _errorToast

    private val _errorToastUsername = MutableLiveData<Boolean>()

    val errorToastUsername: LiveData<Boolean>
        get() = _errorToastUsername


    fun submitButton() {
        Log.i("RegisterViewModel", "Inside SUBMIT BUTTON")
        Log.i(
            "RegisterViewModel",
            "fname null: " + (inputFirstName.value == null) + " ${inputFirstName.value}"
        )
        Log.i(
            "RegisterViewModel",
            "lname null: " + (inputLastName.value == null) + " ${inputLastName.value}"
        )
        Log.i(
            "RegisterViewModel",
            "email null: " + (inputEmail.value == null) + " ${inputEmail.value}"
        )
        Log.i(
            "RegisterViewModel",
            "username null: " + (inputUsername.value == null) + " ${inputUsername.value}"
        )
        Log.i(
            "RegisterViewModel",
            "password null: " + (inputPassword.value == null) + " ${inputPassword.value}"
        )
        if (inputFirstName.value == null || inputLastName.value == null || inputEmail.value == null ||
            inputUsername.value == null || inputPassword.value == null) {
            _errorToast.value = true
        } else {
            uiScope.launch {
//            withContext(Dispatchers.IO) {
                val usersNames = repository.getUserName(inputUsername.value!!)
                if (usersNames != null) {
                    _errorToastUsername.value = true
                    Log.i("RegisterViewModel", "user name not null")
                } else {
                    Log.i("RegisterViewModel", "Successful Registration!")
                    val firstName = inputFirstName.value!!
                    val lastName = inputLastName.value!!
                    val email = inputEmail.value!!
                    val username = inputUsername.value!!
                    val password = inputPassword.value!!
                    insert(RegisterEntity(0, firstName, lastName, email, username, password, "movies"))
                    inputFirstName.value = null
                    inputLastName.value = null
                    inputEmail.value = null
                    inputUsername.value = null
                    inputPassword.value = null
                    _navigateto.value = true
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun doneNavigating() {
        _navigateto.value = false
        Log.i("RegisterViewModel", "Done navigating ")
    }

    fun doneToast() {
        _errorToast.value = false
        Log.i("RegisterViewModel", "Done toasting ")
    }

    fun doneToastUserName() {
        _errorToast.value = false
        Log.i("RegisterViewModel", "Done toasting  username")
    }

    private fun insert(user: RegisterEntity): Job = viewModelScope.launch {
        repository.insert(user)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}





