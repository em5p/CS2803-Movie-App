package com.example.movieapp.login

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.movieapp.R

class LoginFragmentDirections private constructor() {
  companion object {
      fun actionLoginFragmentToHomeFragment(): NavDirections =
          ActionOnlyNavDirections(R.id.action_loginFragment_to_homeFragment)
  }
}
