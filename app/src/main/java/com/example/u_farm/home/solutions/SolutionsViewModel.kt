package com.example.u_farm.home.solutions

import android.app.Activity
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.u_farm.database.AuthRepository
import com.example.u_farm.model.Problem
import com.example.u_farm.model.U_Farm

class SolutionsViewModel(application: Application,activity: Activity): ViewModel()  {
    private var _navigateToAddSolutions= MutableLiveData<Boolean>()
    val navigateToAddSolutions: LiveData<Boolean>
        get()=_navigateToAddSolutions

    fun navigateToAddSolutions(){
        _navigateToAddSolutions.value=true
    }
    fun navigateToAddSolutionsDone(){
        _navigateToAddSolutions.value=false
    }
    private var authRepository: AuthRepository
    init{
        authRepository= AuthRepository(application)
        authRepository.ProblemDataList()

    }
    val allData: MutableLiveData<MutableList<Problem?>>
        get()=authRepository.ProblemDataMutableLiveDataList()



}
