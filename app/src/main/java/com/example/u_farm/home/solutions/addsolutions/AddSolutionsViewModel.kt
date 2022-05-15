package com.example.u_farm.home.solutions.addsolutions

import android.app.Activity
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddSolutionsViewModel(application: Application, activity: Activity): ViewModel() {
    private var _postSolutions= MutableLiveData<Boolean>()
    val postSolutions: LiveData<Boolean>
        get()=_postSolutions

    fun navigateToHomePage(){
        _postSolutions.value=true
    }
    fun navigateToHomePageDone(){
        _postSolutions.value=false
    }


}