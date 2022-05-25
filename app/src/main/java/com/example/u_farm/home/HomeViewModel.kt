package com.example.u_farm.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.u_farm.database.AuthRepository
import com.example.u_farm.model.Problem
import com.example.u_farm.model.U_Farm
import com.google.firebase.auth.FirebaseUser

class HomeViewModel(application: Application): ViewModel() {

    private var authRepository: AuthRepository
    private val _navigateToSolutionsPage= MutableLiveData<String>()
    val navigateToSolutionsPage: LiveData<String>
        get()=_navigateToSolutionsPage

    private val _navigateToAddProblemsPage= MutableLiveData<Boolean>()
    val navigateToAddProblemsPage: LiveData<Boolean>
        get()=_navigateToAddProblemsPage


    fun navigateToSolutionsPageDone() {
        _navigateToSolutionsPage.value=""

    }

    fun navigateToAddProblemsPage(){
        _navigateToAddProblemsPage.value=true
    }

    fun navigateToAddProblemsPageDone(){
        _navigateToAddProblemsPage.value=false
    }

    fun navigateToSolutionsPage(puid: String) {
        _navigateToSolutionsPage.value=puid

    }
    val newData: LiveData<Boolean?>
        get()= authRepository.setProblemDataMutableLiveData()

   fun function(){
       authRepository.ProblemDataList()
   }


    init{
        authRepository= AuthRepository(application)
        authRepository.ProblemDataList()

    }
    val allData: MutableLiveData<MutableList<Problem?>>
        get()=authRepository.ProblemDataMutableLiveDataList()


}