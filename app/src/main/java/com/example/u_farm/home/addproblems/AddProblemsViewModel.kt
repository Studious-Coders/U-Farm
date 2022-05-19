package com.example.u_farm.home.addproblems

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.media.AudioFormat
import android.media.AudioFormat.ENCODING_PCM_16BIT
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.u_farm.database.AuthRepository
import com.example.u_farm.model.Problem
import com.example.u_farm.model.U_Farm
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.io.File
import java.io.IOException

private const val LOG_TAG = "AudioRecordTest"

class AddProblemsViewModel(application: Application,activity: Activity): ViewModel() {
    var _image= MutableLiveData<Boolean>()
    val image: LiveData<Boolean>
        get()=_image

    private var _spinner= MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean>
        get()=_spinner

    private var _convertAudio= MutableLiveData<String>()
    val convertAudio: LiveData<String>
        get()=_convertAudio

    private var authRepository: AuthRepository

    val getData: LiveData<U_Farm?>
        get()=authRepository.getUserDataMutableLiveData()

    val setData: LiveData<Boolean?>
        get()=authRepository.setProblemDataMutableLiveData()

    val setImage:LiveData<String?>
        get()=authRepository.uploadedDataMutuableLiveData()

    init {
        authRepository = AuthRepository(application)
        authRepository.getUserData()
        authRepository.ProblemDataList()

    }

    fun imageFormating() {
        _image.value=true
    }
    var str1:String?=null
    var str:String?=""

    var str2:String?=""



    val key:String= authRepository.reference1.key.toString()

    fun postProblems(){
        if(setImage.value!=null) {
            str1=setImage.value.toString()
        }else{
            str1= ""
        }


        val problem= Problem(key,getData.value!!.uid,getData.value!!.username,getData.value!!.profilePicture,str!!,str1!!,str2!!)
        authRepository.setProblemData(problem)
        _spinner.value=true
    }

    fun function(){
        _spinner.value=false
    }


    fun imageFormatingDone(dp: Uri){
        authRepository.uploadImageToFirebaseStorage(dp,"diseasesAffectedPlants")
        _image.value=false
    }

    private var recorder: MediaRecorder? = null

    private var player: MediaPlayer? = null
//    private var filename:String?=null
    
    fun startRecording(folder:String){
//        filename=folder
          recorder = MediaRecorder().apply {
              setAudioSource(MediaRecorder.AudioSource.MIC)
              setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
              setOutputFile(folder)
              setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

//              setOutputFormat(MediaRecorder.ENCODING_PCM_16BIT);
//              setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
//              setAudioChannels(1);
//              setAudioEncodingBitRate(128000);
//              setAudioSamplingRate(48000);
//              setOutputFile(fileName);

              try {
                  prepare()
              } catch (e: IOException) {
                  Log.e(LOG_TAG, "prepare() failed")
              }

              start()
          }
          convertAudioToText()
      }

    fun stopRecording(){
        recorder?.apply {
            stop()
            release()
        }
        recorder = null

    }

    fun saveAudio(folder:String){
        val uri:Uri=Uri.fromFile(File(folder))
        authRepository.uploadImageToFirebaseStorage(uri,"AudioToText")
    }


    fun convertAudioToText(){

       _convertAudio.value="ConvertText"
        str=_convertAudio.value

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