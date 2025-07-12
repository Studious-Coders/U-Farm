package com.example.u_farm.news

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewsViewModelFactory( private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(application) as T
            //Returns the values from the fragment
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}
