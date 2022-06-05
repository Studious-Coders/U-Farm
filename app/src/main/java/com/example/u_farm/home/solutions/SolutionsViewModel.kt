package com.example.u_farm.home.solutions

import android.app.Activity
import android.app.Application
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.*
import com.chaquo.python.Python
import com.example.u_farm.database.AuthRepository
import com.example.u_farm.model.Problem
import com.example.u_farm.model.Solution
import com.example.u_farm.model.U_Farm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.util.*

class SolutionsViewModel(application: Application,problemUid: String): ViewModel()  {
    var puid:String
    private var _navigateToAddSolutions= MutableLiveData<Boolean>()
    val navigateToAddSolutions: LiveData<Boolean>
        get()=_navigateToAddSolutions

    private var _read= MutableLiveData<String?>()
    val read: LiveData<String?>
        get()=_read

//
//    val singleChanges: LiveData<Boolean?>
//        get()=authRepository.singleRecordDataMutuableLiveData()


    val get: LiveData<U_Farm?>
        get()=authRepository.getUserDataMutableLiveData()

    val textToSpeechEngine: TextToSpeech by lazy {
        TextToSpeech(application) {
            if (it == TextToSpeech.SUCCESS) {
                textToSpeechEngine.language = Locale(get.value!!.language)

            }
        }
    }



    fun increaseRating(inc:Int,suid:String){
            authRepository.singleRecordSolution(inc+1,"rating",suid)
            Log.d("Manogari",puid)
            initializeSolutionList()

    }

    fun decreaseRating(dec:Int,suid:String) {
        authRepository.singleRecordSolution(dec-1,"rating",suid)
        Log.d("Manogari",puid)
        initializeSolutionList()

    }

//    fun intiate(){
//        authRepository.SolutionDataList(puid)
//    }
//


    lateinit var textToSpeechEngine1: TextToSpeech



    fun initial(engine: TextToSpeech, ) = viewModelScope.launch {
        textToSpeechEngine1 = engine
    }

    fun speak(text: String) = viewModelScope.launch{
        textToSpeechEngine1.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    fun navigateToAddSolutions(){
        _navigateToAddSolutions.value=true
    }
    fun navigateToAddSolutionsDone(){
        _navigateToAddSolutions.value=false

    }

    fun textToSpeech(str:String){
        _read.value=str
     }

    fun textToSpeechDone(){
        _read.value=null
    }
    val value= MediatorLiveData<Problem>()

    val getData: LiveData<Problem?>
        get()=authRepository.getProblemMutableLiveData()

    private var authRepository: AuthRepository
    init{
        authRepository= AuthRepository(application)
        authRepository.SolutionDataList(problemUid)
        authRepository.getProblem(problemUid)
        authRepository.getUserData()
        value.addSource(getData,value::setValue)
        puid=problemUid
    }

    fun initiate(){
        authRepository.SolutionDataList(puid)
    }

    fun initializeSolutionList(){
        viewModelScope.launch{
            run()
          }

        }

    private suspend fun run(){
        withContext(Dispatchers.IO){
            authRepository.SolutionDataList(puid)

        }
    }



    val allData: MutableLiveData<MutableList<Solution?>>
        get()=authRepository.SolutionDataMutableLiveDataList()
}



