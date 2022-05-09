package com.example.u_farm.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.u_farm.database.AuthRepository
import com.example.u_farm.model.U_Farm
import com.google.firebase.auth.FirebaseUser

class HomeViewModel(application: Application): ViewModel() {

    private var authRepository: AuthRepository
    private val _navigateToSolutionsPage= MutableLiveData<Boolean>()
    val navigateToSolutionsPage: LiveData<Boolean>
        get()=_navigateToSolutionsPage

    fun navigateToSolutionPage(){
        _navigateToSolutionsPage.value=true
    }

    fun navigateToSolutionsPageDone(){
        _navigateToSolutionsPage.value=false
    }



    init{
        authRepository= AuthRepository(application)
        authRepository.getUserDataList()

    }
    val allData: MutableLiveData<MutableList<U_Farm?>>
        get()=authRepository.userDataMutableLiveDataList()


}