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
import com.example.u_farm.util.lang
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

    val getData: LiveData<U_Farm?>
        get()=authRepository.getUserDataMutableLiveData()

    private var _expection= MutableLiveData<Boolean>()
    val expection: LiveData<Boolean>
        get()=_expection

    private var _uploaded= MutableLiveData<Boolean>()
    val uploaded: LiveData<Boolean>
        get()=_uploaded

    fun postSolutions(solutionStatement:String,rating:Int){
        val numberOfInputWords:Int
        val words =solutionStatement.trim()
        numberOfInputWords= words.split("\\s+".toRegex()).size
        Log.d("Words in a Sentence",words+numberOfInputWords)

        if(numberOfInputWords<10){
        if(solutionStatement.length<50){
            _expection.value=true
        }else{
        viewModelScope.launch {
            val solution= Solution(key1, puid,getData.value!!.uid,getData.value!!.username,getData.value!!.profilePicture,solutionStatement,rating)
            upload(solution)
            _uploaded.value=true
        }
       }
    }

    private suspend fun upload(solution:Solution){
        withContext(Dispatchers.IO){
            authRepository.setSolutionData(solution)
        }
    }

    fun uploaded(){
        _uploaded.value=false
    }



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
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, lang)
            putExtra(RecognizerIntent.EXTRA_PROMPT, Locale("Bicara sekarang"))
        })
    }
}

