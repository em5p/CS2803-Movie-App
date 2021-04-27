package com.example.movieapp.login

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.fragment_login.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.movieapp.MovieListScreen
import com.example.movieapp.R
import com.example.movieapp.database.RegisterDatabase
import com.example.movieapp.database.RegisterRepository
import com.example.movieapp.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login, container, false
        )

        val application = requireNotNull(this.activity).application

        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao

        val repository = RegisterRepository(dao)

        val factory = LoginViewModelFactory(repository, application)

        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        binding.myLoginViewModel = loginViewModel

        binding.lifecycleOwner = this

        loginViewModel.navigatetoRegister.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                Log.i("LoginFragment", "inside navigate to register observe")
                displayUsersList()
                loginViewModel.doneNavigatingRegister()
            }
        })

        loginViewModel.navigatetoMovieListScreen.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished == true) {
                Log.i("LoginFragment", "inside observe for nav to movie list screen")
//                submitButton.setOnClickListener {
                requireActivity().run {
                    startActivity(Intent(this, MovieListScreen::class.java))
                    finish()
//                    }
                }
                loginViewModel.doneNavigatingToMovieListScreen()
            }
        })

        fun showAlertDialog() {
            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@LoginFragment.context)
            alertDialog.setTitle("Invalid Login")
            alertDialog.setMessage("Please fill in all fields")
            alertDialog.setPositiveButton(
                    "Ok"
            ) { _, _ ->
                Toast.makeText(this@LoginFragment.context, "Alert dialog closed.", Toast.LENGTH_LONG).show()
            }
            val alert: AlertDialog = alertDialog.create()
            alert.setCanceledOnTouchOutside(false)
            alert.show()
        }

        loginViewModel.errorToast.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
                Log.i("LoginFragment", "inside error toast, fields not all filled")
//                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT)
//                    .show()
                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@LoginFragment.context)
                alertDialog.setTitle("Invalid Login")
                alertDialog.setMessage("Please fill in all fields")
                alertDialog.setPositiveButton(
                    "Ok"
                ) { _, _ ->
                    Toast.makeText(this@LoginFragment.context, "Alert dialog closed.", Toast.LENGTH_LONG).show()
                }
                val alert: AlertDialog = alertDialog.create()
                alert.setCanceledOnTouchOutside(false)
                alert.show()
                loginViewModel.donetoast()
            }
        })

        loginViewModel.errorToastUsername.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
//                Toast.makeText(
//                    requireContext(), "User doesn't exist, please register " +
//                            "or check the username you entered", Toast.LENGTH_SHORT
//                ).show()
                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@LoginFragment.context)
                alertDialog.setTitle("Invalid Login")
                alertDialog.setMessage("User does not exist. Please register or " +
                        "double check username entered")
                alertDialog.setPositiveButton(
                    "Ok"
                ) { _, _ ->
                    Toast.makeText(this@LoginFragment.context, "Alert dialog closed.", Toast.LENGTH_LONG).show()
                }
                val alert: AlertDialog = alertDialog.create()
                alert.setCanceledOnTouchOutside(false)
                alert.show()
                loginViewModel.donetoastErrorUsername()
            }
        })

        loginViewModel.errorToastInvalidPassword.observe(viewLifecycleOwner, Observer { hasError ->
            if (hasError == true) {
//                Toast.makeText(requireContext(), "Please check your password", Toast.LENGTH_SHORT)
//                    .show()
                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@LoginFragment.context)
                alertDialog.setTitle("Invalid Login")
                alertDialog.setMessage("Invalid username/password combination. Please check your password")
                alertDialog.setPositiveButton(
                    "Ok"
                ) { _, _ ->
                    Toast.makeText(this@LoginFragment.context, "Alert dialog closed.", Toast.LENGTH_LONG).show()
                }
                val alert: AlertDialog = alertDialog.create()
                alert.setCanceledOnTouchOutside(false)
                alert.show()
                loginViewModel.donetoastInvalidPassword()
            }
        })
        return binding.root
    }


    private fun displayUsersList() {
        Log.i("LoginFragment", "inside display users list function")
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        NavHostFragment.findNavController(this).navigate(action)

    }

}