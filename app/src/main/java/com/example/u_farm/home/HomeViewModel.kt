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

//    private val _choselang= MutableLiveData<Boolean>()
//    val choselang: LiveData<Boolean>
//        get()=_choselang

    val getData: LiveData<U_Farm?>
        get()=authRepository.getUserDataMutableLiveData()

   // lateinit var chosenlang:String



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

//    fun languagechange(){
//        chosenlang=getData.value!!.language
//        _choselang.value=false
//    }

    //var chosedlang=getData.value!!.language


    init{
        authRepository= AuthRepository(application)
        authRepository.getUserData()
        authRepository.ProblemDataList()
       // _choselang.value=true
       //chosedlang=getData.value!!.language

    }
    val allData: MutableLiveData<MutableList<Problem?>>
        get()=authRepository.ProblemDataMutableLiveDataList()


}