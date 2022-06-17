package com.example.u_farm.home.solutions

import android.app.Application
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.*
import com.chaquo.python.Python
import com.example.u_farm.database.AuthRepository
import com.example.u_farm.model.Problem
import com.example.u_farm.model.Solution
import com.example.u_farm.model.U_Farm
import com.example.u_farm.util.lang
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class SolutionsViewModel(application: Application,problemUid: String): ViewModel()  {
    var puid:String
    private var _navigateToAddSolutions= MutableLiveData<Boolean>()
    val navigateToAddSolutions: LiveData<Boolean>
        get()=_navigateToAddSolutions

    private var _read= MutableLiveData<String?>()
    val read: LiveData<String?>
        get()=_read

    val textToSpeechEngine: TextToSpeech by lazy {
        TextToSpeech(application) {
            if (it == TextToSpeech.SUCCESS) {
                Log.d("Success",it.toString()+ lang)
                 if(lang=="ta_IN"){
                     textToSpeechEngine.language= Locale("ta-IN")
                 }else if(lang=="hi_IN"){
                     textToSpeechEngine.language=Locale("hi-IN")
                 }else{
                     textToSpeechEngine.language=Locale("en-US")
                 }
                }

        }
    }

    val get: LiveData<U_Farm?>
        get()=authRepository.getUserDataMutableLiveData()

    fun speakOutTheProblem(problemStatement:String){
        Log.d("Priya",problemStatement+textToSpeechEngine)
        initial(textToSpeechEngine)
        var text = problemStatement.trim()

        val py = Python.getInstance()
        val pyobj = py.getModule("translate")
        text=pyobj.callAttr(lang,text).toString()
        Log.d("priya1",text)
        speak(if (text.isNotEmpty()) text else "Text tidak boleh kosong")
    }


    fun increaseRating(inc:Int,suid:String){
        viewModelScope.launch{
            initiate()
            incrementAndDecrement(inc+1,suid)
        }

    }

    fun decreaseRating(dec:Int,suid:String) {
        viewModelScope.launch {
            initiate()
            incrementAndDecrement(dec-1,suid)
        }
    }

    private suspend fun incrementAndDecrement(calculation:Int,solutionUid:String){
        withContext(Dispatchers.IO) {
            authRepository.singleRecordSolution(calculation, "rating", solutionUid)
        }
    }

    lateinit var textToSpeechEngine1: TextToSpeech

    fun initial(engine: TextToSpeech,) = viewModelScope.launch {
        Log.d("Success",engine.toString())
        textToSpeechEngine1 = engine
    }

    fun speak(text: String) = viewModelScope.launch{
        textToSpeechEngine1.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    fun navigateToAddSolutions(){
        initiate()
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
        allData.value?.clear()
    }

    val allData: MutableLiveData<MutableList<Solution?>>
        get()=authRepository.SolutionDataMutableLiveDataList()


       }



