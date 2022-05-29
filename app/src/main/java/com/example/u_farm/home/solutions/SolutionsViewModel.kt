package com.example.u_farm.home.solutions

import android.app.Activity
import android.app.Application
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.speech.tts.TextToSpeech
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.u_farm.database.AuthRepository
import com.example.u_farm.model.Solution
import com.example.u_farm.model.U_Farm
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

class SolutionsViewModel(application: Application,activity: Activity): ViewModel()  {
    private var _navigateToAddSolutions= MutableLiveData<Boolean>()
    val navigateToAddSolutions: LiveData<Boolean>
        get()=_navigateToAddSolutions

    private var _read= MutableLiveData<String?>()
    val read: LiveData<String?>
        get()=_read


    val singleChanges: LiveData<Boolean?>
        get()=authRepository.singleRecordDataMutuableLiveData()




    fun increaseRating(inc:Int,suid:String){
            authRepository.singleRecordSolution(inc+1,"rating",suid)

    }

    fun decreaseRating(dec:Int,suid:String) {

            authRepository.singleRecordSolution(dec-1,"rating",suid)


    }

    fun intiate(){
        authRepository.SolutionDataList(read.toString())
    }


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
        _read.value=str
    }
    fun textToSpeechDone(){
        _read.value=null
    }


    fun getSolutionOfTheGetProblem(args1: String) {

        authRepository.SolutionDataList(args1)

    }

    private var authRepository: AuthRepository
    init{
        authRepository= AuthRepository(application)
        _argument.value=true



    }

    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null



    val allData: MutableLiveData<MutableList<Solution?>>
        get()=authRepository.SolutionDataMutableLiveDataList()  }



