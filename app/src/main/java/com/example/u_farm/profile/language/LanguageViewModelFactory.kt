package com.example.u_farm.profile.language

import android.app.Activity
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.u_farm.profile.editprofile.EditProfileViewModel

class LanguageViewModelFactory(private val application: Application, private val activity: Activity) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.N)
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LanguageViewModel::class.java)) {
            return LanguageViewModel(application,activity) as T
            //Returns the values from the fragment
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}

