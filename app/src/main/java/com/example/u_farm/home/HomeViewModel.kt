package com.example.u_farm.home

import android.app.Application
import android.speech.tts.TextToSpeech
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaquo.python.Python
import com.example.u_farm.database.AuthRepository
import com.example.u_farm.model.Problem
import com.example.u_farm.model.U_Farm
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel(application: Application): ViewModel() {

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
//    val newData: LiveData<Boolean?>
//        get()= authRepository.setProblemDataMutableLiveData()

    private val _newData= MutableLiveData<Boolean?>()
    val newData: LiveData<Boolean?>
        get()=authRepository.setProblemDataMutableLiveData()




    fun function(){
       authRepository.ProblemDataList()
       _newData.value=false
   }

    //var chosedlang=getData.value!!.language


    init{
        authRepository= AuthRepository(application)
        authRepository.getUserData()
        authRepository.ProblemDataList()

    }
    val allData: MutableLiveData<MutableList<Problem?>>
        get()=authRepository.ProblemDataMutableLiveDataList()

    fun convert()
    {

        val py = Python.getInstance();
        val pyobj = py.getModule("translate")
        val text="Solution"
        val converttext= pyobj.callAttr("tam",text)
        //return converttext
    }


}