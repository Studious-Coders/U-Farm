package com.example.u_farm.home.solutions.addsolutions

import android.app.Activity
import android.app.Application
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.content.Intent
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.u_farm.database.AuthRepository
import com.example.u_farm.model.Problem
import com.example.u_farm.model.Solution
import com.example.u_farm.model.U_Farm
import java.io.IOException
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
class AddSolutionsViewModel(application: Application,problemUid:String): ViewModel() {
    private var authRepository: AuthRepository
    var puid:String
    init {
        authRepository = AuthRepository(application)
        authRepository.getUserData()
       puid=problemUid

    }
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    val key1:String= authRepository.reference2.key.toString()

    val setData:LiveData<Boolean?>
        get()=authRepository.setSolutionDataMutableLiveData()

    val getData: LiveData<U_Farm?>
        get()=authRepository.getUserDataMutableLiveData()

    private var _expection= MutableLiveData<Boolean>()
    val expection: LiveData<Boolean>
        get()=_expection



    fun postSolutions(solutionStatement:String,rating:Int){
        if(solutionStatement.length<50){
            _expection.value=true
        }else{
        viewModelScope.launch {
            val solution= Solution(key1, puid,getData.value!!.uid,getData.value!!.username,getData.value!!.profilePicture,solutionStatement,rating)
            upload(solution)
        }
       }
    }

    private suspend fun upload(solution:Solution){
        withContext(Dispatchers.IO){
            authRepository.setSolutionData(solution)
         }

    }


    val get: MutableLiveData<MutableList<Solution?>>
        get()=authRepository.SolutionDataMutableLiveDataList()


    fun initial(
        launcher: ActivityResultLauncher<Intent>
    ) = viewModelScope.launch {
        startForResult = launcher
    }


    fun startRecording() {
        startForResult.launch(Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, getData.value!!.language)
            putExtra(RecognizerIntent.EXTRA_PROMPT, Locale("Bicara sekarang"))
        })
    }
}

