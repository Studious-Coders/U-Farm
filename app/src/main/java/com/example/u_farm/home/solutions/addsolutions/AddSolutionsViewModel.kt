package com.example.u_farm.home.solutions.addsolutions

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.*

class AddSolutionsViewModel(application: Application, activity: Activity): ViewModel() {
    private var _postSolutions= MutableLiveData<Boolean>()
    val postSolutions: LiveData<Boolean>
        get()=_postSolutions

    var str1:String?=null

    fun navigateToHomePage(){
        _postSolutions.value=true
    }
    fun navigateToHomePageDone(){
        _postSolutions.value=false
    }

    private lateinit var startForResult: ActivityResultLauncher<Intent>

    fun initial(
        launcher: ActivityResultLauncher<Intent>
    ) = viewModelScope.launch {
//        textToSpeechEngine = engine
        startForResult = launcher
    }

    fun startRecording() {

        startForResult.launch(Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("in_ID"))
            putExtra(RecognizerIntent.EXTRA_PROMPT, Locale("Bicara sekarang"))
        })
    }

    private var _edit= MutableLiveData<Boolean>()
    val edit: LiveData<Boolean>
        get()=_edit


    fun edit(){
        _edit.value=true
    }

    fun editDone(){
        _edit.value=false
    }


}