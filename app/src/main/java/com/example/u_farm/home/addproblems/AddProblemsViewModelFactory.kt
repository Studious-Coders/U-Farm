package com.example.u_farm.home.addproblems


import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddProblemsViewModelFactory( private val application: Application) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.N)
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddProblemsViewModel::class.java)) {
            return AddProblemsViewModel(application) as T
            //Returns the values from the fragment
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}

