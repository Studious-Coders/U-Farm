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

class AddSolutionsViewModel(application: Application, activity: Activity): ViewModel() {

    private var _spinner= MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean>
        get()=_spinner


    private lateinit var authRepository: AuthRepository

    val getData: LiveData<U_Farm?>
        get()=authRepository.getUserDataMutableLiveData()

    val setData: LiveData<Boolean?>
        get()=authRepository.setSolutionDataMutableLiveData()


    var str:String?=null

    var str1:String?=null

//    fun navigateToHomePage(){
//        _postSolutions.value=true
    fun arguments(args:String){
        str=args
    }

    init {
        authRepository = AuthRepository(application)
        authRepository.getUserData()
       _spinner.value=true
//        Log.d("Dhanush2","${str}")
//        authRepository.SolutionDataList(str!!)

    }

    fun argumentsPassed(){
        _spinner.value=false
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

    val key1:String= authRepository.reference2.key.toString()

   fun postSolutions(solutionStatement:String){
          Log.d("Dhanush1","${str}")
        val solution= Solution(key1,str!!,getData.value!!.uid,getData.value!!.username,"",solutionStatement,"")
        authRepository.setSolutionData(solution)

    }


//    private var recorder: MediaRecorder? = null
//    private var player: MediaPlayer? = null
//    fun convertAudioToText(){
//        speech = SpeechRecognizer.createSpeechRecognizer(this)
////        Log.i(logTag, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(this))
//        speech.setRecognitionListener(this)
//        recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "US-en")
//        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
//        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
//
//        if (isChecked) {
//            progressBar.visibility = View.VISIBLE
//            progressBar.isIndeterminate = true
//            ActivityCompat.requestPermissions(this@MainActivity,
//                arrayOf(Manifest.permission.RECORD_AUDIO),
//                permission)
//        } else {
//            progressBar.isIndeterminate = false
//            progressBar.visibility = View.VISIBLE
//            speech.stopListening()
//        }
//
//        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>,
//                                                grantResults: IntArray) {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//            when (requestCode) {
//                permission -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager
//                        .PERMISSION_GRANTED) {
//                    speech.startListening(recognizerIntent)
//                } else {
//                    Toast.makeText(this@MainActivity, "Permission Denied!",
//                        Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//        override fun onStop() {
//            super.onStop()
//            speech.destroy()
//            Log.i(logTag, "destroy")
//        }
//        override fun onReadyForSpeech(params: Bundle?) {
//            TODO("Not yet implemented")
//        }
//        override fun onRmsChanged(rmsdB: Float) {
//            progressBar.progress = rmsdB.toInt()
//        }
//        override fun onBufferReceived(buffer: ByteArray?) {
//            TODO("Not yet implemented")
//        }
//        override fun onPartialResults(partialResults: Bundle?) {
//            TODO("Not yet implemented")
//        }
//        override fun onEvent(eventType: Int, params: Bundle?) {
//            TODO("Not yet implemented")
//        }
//        override fun onBeginningOfSpeech() {
//            Log.i(logTag, "onBeginningOfSpeech")
//            progressBar.isIndeterminate = false
//            progressBar.max = 10
//        }
//        override fun onEndOfSpeech() {
//            progressBar.isIndeterminate = true
//            toggleButton.isChecked = false
//        }
//        override fun onError(error: Int) {
//            val errorMessage: String = getErrorText(error)
//            Log.d(logTag, "FAILED $errorMessage")
//            returnedText.text = errorMessage
//            toggleButton.isChecked = false
//        }
//        private fun getErrorText(error: Int): String {
//            var message = ""
//            message = when (error) {
//                SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
//                SpeechRecognizer.ERROR_CLIENT -> "Client side error"
//                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
//                SpeechRecognizer.ERROR_NETWORK -> "Network error"
//                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
//                SpeechRecognizer.ERROR_NO_MATCH -> "No match"
//                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RecognitionService busy"
//                SpeechRecognizer.ERROR_SERVER -> "error from server"
//                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
//                else -> "Didn't understand, please try again."
//            }
//            return message
//        }
//        override fun onResults(results: Bundle?) {
//            Log.i(logTag, "onResults")
//            val matches = results!!.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
//            var text = ""
//            for (result in matches) text = """
//      $result
//      """.trimIndent()
//            returnedText.text = text
////        }
//    }
//    private var _edit= MutableLiveData<Boolean>()
//    val edit: LiveData<Boolean>
//        get()=_edit
//
//
//    fun edit(){
//        _edit.value=true
//    }
//
//    fun editDone(){
//        _edit.value=false
//    }
}