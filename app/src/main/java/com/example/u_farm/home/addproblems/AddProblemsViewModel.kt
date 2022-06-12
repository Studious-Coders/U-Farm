package com.example.u_farm.home.addproblems

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.speech.RecognizerIntent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.u_farm.database.AuthRepository
import com.example.u_farm.model.Problem
import com.example.u_farm.model.U_Farm
import com.example.u_farm.util.lang
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class AddProblemsViewModel(application: Application): ViewModel() {
    private var authRepository: AuthRepository
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    init {
        authRepository = AuthRepository(application)
        authRepository.getUserData()
    }

    val key:String= authRepository.reference1.key.toString()

    var _image= MutableLiveData<Boolean>()
    val image: LiveData<Boolean>
        get()=_image

    private var _uploading= MutableLiveData<Boolean>()
    val uploading: LiveData<Boolean>
        get()=_uploading

    private var _expection= MutableLiveData<Boolean>()
    val expection: LiveData<Boolean>
        get()=_expection

    val getData: LiveData<U_Farm?>
        get()=authRepository.getUserDataMutableLiveData()

    val setImage:LiveData<String?>
        get()=authRepository.uploadedDataMutuableLiveData()

    fun imageFormating() {
        _image.value=true
    }

    fun imageFormatingDone(dp: Uri){
        viewModelScope.launch {
            uploadImageToStorage(dp)
            _image.value=false
        }
        }

    private suspend fun uploadImageToStorage(uriLink:Uri){
        withContext(Dispatchers.IO) {
            authRepository.uploadImageToFirebaseStorage(uriLink, "diseasesAffectedPlants")
        }
    }

    fun postProblems(problemStatement:String){
         val numberOfInputWords:Int
        val words = problemStatement.trim()
        numberOfInputWords= words.split("\\s+".toRegex()).size

        if(problemStatement=="" || setImage.value.toString()=="" || numberOfInputWords<10){
            _expection.value=true
        }else {
            viewModelScope.launch {
                _uploading.value = true
                val problem = Problem(
                    key,
                    getData.value!!.uid,
                    getData.value!!.username,
                    getData.value!!.profilePicture,
                    problemStatement,
                    setImage.value.toString()
                )
                upload(problem)
               }
        }
    }

    private suspend fun upload(problem: Problem){
     withContext(Dispatchers.IO){
          authRepository.setProblemData(problem)
          }
    }

    fun uploaded(){
        _uploading.value=false
        _expection.value=false
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