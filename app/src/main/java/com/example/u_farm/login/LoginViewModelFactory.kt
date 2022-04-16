package com.example.u_farm.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides the key for the night and the SleepDatabaseDao to the ViewModel.
 */
class LoginViewModelFactory (private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(application) as T
            //Returns the values from the fragment
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}
