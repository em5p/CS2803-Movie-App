package com.example.movieapp.register

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.movieapp.R

class RegisterFragmentDirections private constructor() {
  companion object {
    fun actionRegisterFragmentToLoginFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_registerFragment_to_loginFragment)
  }
}
