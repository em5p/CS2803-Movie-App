package com.example.movieapp.register

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.movieapp.R
import com.example.movieapp.database.RegisterDatabase
import com.example.movieapp.database.RegisterRepository
import com.example.movieapp.databinding.FragmentRegisterBinding


/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRegisterBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_register, container, false
        )

        val application = requireNotNull(this.activity).application

        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao

        val repository = RegisterRepository(dao)

        val factory = RegisterViewModelFactory(repository, application)

        registerViewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)

        binding.myViewModel = registerViewModel

        binding.lifecycleOwner = this
        
        registerViewModel.navigateto.observe(viewLifecycleOwner, Observer { hasFinished->
            if (hasFinished == true){
                Log.i("RegisterFragment","inside navigate to observe")
                displayUsersList()
                registerViewModel.doneNavigating()
            }
        })

        registerViewModel.userDetailsLiveData.observe(viewLifecycleOwner, Observer {
            Log.i("RegisterFragment",it.toString()+"000000000000000000000000")
        })


        registerViewModel.errorToast.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
//                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@RegisterFragment.context)
                alertDialog.setTitle("Registration Error")
                alertDialog.setMessage("Please fill in all fields")
                alertDialog.setPositiveButton(
                    "Ok"
                ) { _, _ ->
                    Toast.makeText(this@RegisterFragment.context, "Alert dialog closed.", Toast.LENGTH_LONG).show()
                }
                val alert: AlertDialog = alertDialog.create()
                alert.setCanceledOnTouchOutside(false)
                alert.show()
                registerViewModel.doneToast()
            }
        })

        registerViewModel.errorToastUsername.observe(viewLifecycleOwner, Observer { hasError->
            if(hasError==true){
//                Toast.makeText(requireContext(), "UserName Already taken", Toast.LENGTH_SHORT).show()
                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@RegisterFragment.context)
                alertDialog.setTitle("Registration Error")
                alertDialog.setMessage("Username is taken. Please choose a different username.")
                alertDialog.setPositiveButton(
                    "Ok"
                ) { _, _ ->
                    Toast.makeText(this@RegisterFragment.context, "Alert dialog closed.", Toast.LENGTH_LONG).show()
                }
                val alert: AlertDialog = alertDialog.create()
                alert.setCanceledOnTouchOutside(false)
                alert.show()
                registerViewModel.doneToast()
                registerViewModel.doneToastUserName()
            }
        })

        return binding.root
    }

    private fun displayUsersList() {
        Log.i("RegisterFragment","inside display users list function")
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        NavHostFragment.findNavController(this).navigate(action)

    }

}