package com.example.u_farm.home.solutions

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.u_farm.database.AuthRepository
import com.example.u_farm.model.Problem
import com.example.u_farm.model.Solution
import com.example.u_farm.model.U_Farm
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

class SolutionsViewModel(application: Application,activity: Activity): ViewModel()  {
    private var _navigateToAddSolutions= MutableLiveData<Boolean>()
    val navigateToAddSolutions: LiveData<Boolean>
        get()=_navigateToAddSolutions

    private var _read= MutableLiveData<Boolean>()
    val read: LiveData<Boolean>
        get()=_read


    private var _argument= MutableLiveData<Boolean>()
    val argument: LiveData<Boolean>
        get()=_argument


    lateinit var textToSpeechEngine: TextToSpeech



    fun initial(engine: TextToSpeech, ) = viewModelScope.launch {
        textToSpeechEngine = engine
    }

    fun speak(text: String) = viewModelScope.launch{
        textToSpeechEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    fun navigateToAddSolutions(){
        _navigateToAddSolutions.value=true
    }
    fun navigateToAddSolutionsDone(){
        _navigateToAddSolutions.value=false

    }

    fun textToSpeech(str:String){
        _read.value=true
    }
    fun textToSpeechDone(){
        _read.value=false
    }


    fun getSolutionOfTheGetProblem(args1: SolutionsActivityArgs) {

        authRepository.SolutionDataList(args1.problemUid.toString())
    }

    private var authRepository: AuthRepository
    init{
        authRepository= AuthRepository(application)
        _argument.value=true


    }

    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null



    val allData: MutableLiveData<MutableList<Solution?>>
        get()=authRepository.SolutionDataMutableLiveDataList()
//    fun saveAudio(folder:String){
//        val uri: Uri = Uri.fromFile(File(folder))
//        authRepository.uploadImageToFirebaseStorage(uri,"AudioToText")
//    }
//
//    fun playAudio(folder:String){
//        player = MediaPlayer().apply {
//            try {
//                setDataSource(folder)
//                prepare()
//                start()
//            } catch (e: IOException) {
//                Log.e(LOG_TAG, "prepare() failed")
//            }
//        }
  }



