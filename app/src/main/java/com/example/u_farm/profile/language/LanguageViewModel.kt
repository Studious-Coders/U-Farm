package com.example.u_farm.profile.language

import android.app.Activity
import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.u_farm.database.AuthRepository

class LanguageViewModel(application: Application, activity: Activity): ViewModel() {
    private var authRepository: AuthRepository
    init {
        authRepository = AuthRepository(application)
    }

    fun updateLanguage(lang:String){
        authRepository.singleRecord(lang,"language")
    }
}