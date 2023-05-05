package com.example.u_farm.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.u_farm.database.AuthRepository
import com.example.u_farm.localdatabase.UFarmDatabase.Companion.getInstance
import com.example.u_farm.model.Problem
import com.example.u_farm.model.U_Farm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): ViewModel() {
    val database = getInstance(application)
    private var authRepository: AuthRepository
    private val _navigateToSolutionsPage= MutableLiveData<String>()
    val navigateToSolutionsPage: LiveData<String>
        get()=_navigateToSolutionsPage

    private val _navigateToAddProblemsPage= MutableLiveData<Boolean>()
    val navigateToAddProblemsPage: LiveData<Boolean>
        get()=_navigateToAddProblemsPage

    val getData: LiveData<U_Farm?>
        get()=authRepository.getUserDataMutableLiveData()


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

    var viewModelJob = Job()
    val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    init{
        authRepository= AuthRepository(application)
        authRepository.getUserData()
        authRepository.ProblemDataList()
        coroutineScope.launch{
            authRepository.ProblemDataMutableLiveDataList()
        }
    }
   val allData = authRepository.prob
//    val all: MutableLiveData<MutableList<Problem?>>
//        get()=authRepository.ProblemDataMutableLiveDataList()







}