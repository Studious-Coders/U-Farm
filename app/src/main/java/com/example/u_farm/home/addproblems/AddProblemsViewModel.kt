package com.example.u_farm.home.addproblems

import android.app.Activity
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddProblemsViewModel(application: Application,activity: Activity): ViewModel() {
    private var _postProblems= MutableLiveData<Boolean>()
    val postProblems: LiveData<Boolean>
        get()=_postProblems

    fun navigateToHomePage(){
        _postProblems.value=true
    }
    fun navigateToHomePageDone(){
        _postProblems.value=false
    }

}