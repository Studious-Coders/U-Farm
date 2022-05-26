package com.example.u_farm.home.addproblems

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioFormat.ENCODING_PCM_16BIT
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.u_farm.database.AuthRepository
import com.example.u_farm.model.Problem
import com.example.u_farm.model.U_Farm
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.util.*

private const val LOG_TAG = "AudioRecordTest"

private lateinit var speech: SpeechRecognizer
private lateinit var recognizerIntent: Intent
private var logTag = "AddProblemsViewModel"
class AddProblemsViewModel(application: Application,activity: Activity): ViewModel() {
    var _image= MutableLiveData<Boolean>()
    val image: LiveData<Boolean>
        get()=_image

    private var _uploading= MutableLiveData<Boolean>()
    val uploading: LiveData<Boolean>
        get()=_uploading
    private var authRepository: AuthRepository

    val getData: LiveData<U_Farm?>
        get()=authRepository.getUserDataMutableLiveData()

    val setData: LiveData<Boolean?>
        get()=authRepository.setProblemDataMutableLiveData()

    val setImage:LiveData<String?>
        get()=authRepository.uploadedDataMutuableLiveData()

    private lateinit var textToSpeechEngine: TextToSpeech

    init {
        authRepository = AuthRepository(application)
        authRepository.getUserData()
        authRepository.ProblemDataList()

    }

    fun imageFormating() {
        _image.value=true
    }

    var str:String?=""

    var str1:String?=""


    fun convertAudioToText(convertText :String){
        str1=convertText

    }

    fun uploaded(){
        _uploading.value=false
    }

    val key:String= authRepository.reference1.key.toString()
    var uri:Uri?=null
    fun postProblems(){
        _uploading.value=true

        val problem= Problem(key,getData.value!!.uid,getData.value!!.username,getData.value!!.profilePicture,str1!!,str!!)
        authRepository.setProblemData(problem)

    }
    fun upload(){
        authRepository.uploadImageToFirebaseStorage(uri!!,"images")

    }

    fun upload1(){
        if(setImage.value!=null) {
            str=setImage.value.toString()
        }else{
            str= null
        }
        authRepository.singleRecord(str!!,"diseasesAffectedPlants")

    }




    fun imageFormatingDone(dp: Uri){
        uri=dp
        _image.value=false
    }

    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null

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


//        speech = SpeechRecognizer.createSpeechRecognizer(this)
////        Log.i(logTag, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(this))
//        speech.setRecognitionListener(this)
//        recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "US-en")
//        recognizerIntent.putExtra(
//            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
//        )
//        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)

//          recorder = MediaRecorder().apply {
//              setAudioSource(MediaRecorder.AudioSource.MIC)
//              setOutputFormat(MediaRecorder.OutputFormat.DEFAULT)
//              setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
//              setOutputFile(Environment.getExternalStorageDirectory()
//                  .getAbsolutePath()+"/myrecording.mp3")

//              try {
//                  prepare()
//              } catch (e: IOException) {
//                  Log.e(LOG_TAG, "prepare() failed")
//              }
//
//              start()
//          }
//          convertAudioToText()
}
//
//    fun stopRecording(){
//        recorder?.apply {
//            stop()
//            release()
//        }
//        recorder = null
//
//    }


//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>,
//                                                grantResults: IntArray) {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//            when (requestCode) {
//                Manifest.permission -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager
//                        .PERMISSION_GRANTED) {
//                    speech.startListening(recognizerIntent)
//                } else {
//                    Toast.makeText(this@AddProblemsViewModel, "Permission Denied!",
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
//        }
//       _convertAudio.value="ConvertText"
//        str=_convertAudio.value
//
//
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
//