package com.example.u_farm.home.solutions.addsolutions
import android.app.Activity
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.u_farm.home.addproblems.AddProblemsViewModel

class AddSolutionsViewModelFactory(private val application: Application, private val problemUid: String) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.N)
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddSolutionsViewModel::class.java)) {
            return AddSolutionsViewModel(application,problemUid) as T
            //Returns the values from the fragment
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}
