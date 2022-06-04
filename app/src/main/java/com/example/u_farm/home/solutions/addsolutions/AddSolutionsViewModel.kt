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
import kotlinx.coroutines.launch
import java.util.*
class AddSolutionsViewModel(application: Application,activity: Activity): ViewModel() {
    private var authRepository: AuthRepository
    var _initial= MutableLiveData<Boolean>()
    val initial: LiveData<Boolean>
        get()=_initial

    var _choselang= MutableLiveData<String>()
    val choselang: LiveData<String>
        get()=_choselang

    val setData:LiveData<Boolean?>
        get()=authRepository.setSolutionDataMutableLiveData()

    init {
        authRepository = AuthRepository(application)
        authRepository.getUserData()
        _initial.value=true

    }

    var str:String?=""

    var str1:String?=""


    fun convertAudioToText(convertText :String){
        str1=convertText

    }

    val getData: LiveData<U_Farm?>
        get()=authRepository.getUserDataMutableLiveData()

    val key1:String= authRepository.reference2.key.toString()

    var arguments:String?=""
    fun passArguments(args:String){
          arguments=args
    }


    fun postSolutions(solutionStatement:String,rating:Int){
         val solutionr= Solution(key1,arguments!!,getData.value!!.uid,getData.value!!.username,getData.value!!.profilePicture,solutionStatement,rating)
         authRepository.setSolutionData(solutionr)

     }

    private lateinit var startForResult: ActivityResultLauncher<Intent>

    fun initial(
        launcher: ActivityResultLauncher<Intent>
    ) = viewModelScope.launch {
//        textToSpeechEngine = engine
        startForResult = launcher
    }


    fun startRecording() {

        _choselang.value=getData.value!!.language

        startForResult.launch(Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            if(_choselang.value=="Tamil")
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ta-IN")
            else if(_choselang.value=="English")
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
            else
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "hi-IN")
            putExtra(RecognizerIntent.EXTRA_PROMPT, Locale("Bicara sekarang"))
        })
    }




}

