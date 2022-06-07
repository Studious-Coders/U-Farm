package com.example.u_farm.home.solutions.comment

import android.app.Application
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.u_farm.database.AuthRepository
import com.example.u_farm.model.Comments
import com.example.u_farm.model.Problem
import com.example.u_farm.model.U_Farm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class CommentsViewModel(application: Application, solutionUid:String): ViewModel() {
    private var authRepository: AuthRepository
    var suid:String
    private lateinit var startForResult: ActivityResultLauncher<Intent>


    lateinit var textToSpeechEngine1: TextToSpeech

    private var _read= MutableLiveData<String?>()
    val read: LiveData<String?>
        get()=_read

    private var _expection= MutableLiveData<Boolean?>()
    val expection: LiveData<Boolean?>
        get()=_expection


    val get: LiveData<U_Farm?>
        get()=authRepository.getUserDataMutableLiveData()


    val set: LiveData<Boolean?>
        get()=authRepository.setCommentDataMutableLiveData()




    init{
        authRepository= AuthRepository(application)
        authRepository.getUserData()
        authRepository.CommentsDataList(solutionUid)
        suid=solutionUid
    }

    fun initial(
        launcher: ActivityResultLauncher<Intent>
    ) = viewModelScope.launch {
        startForResult = launcher
    }


    val key2:String= authRepository.reference3.key.toString()

    fun startRecording() {
        startForResult.launch(Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, get.value!!.language)
            putExtra(RecognizerIntent.EXTRA_PROMPT, Locale("Bicara sekarang"))
        })
    }

    fun postComments(commentStatement:String){
        if(commentStatement.length<50){
              _expection.value=true
        }else {
            viewModelScope.launch {
                val commentsData = Comments(
                    key2,
                    suid,
                    get.value!!.uid,
                    get.value!!.username,
                    get.value!!.profilePicture,
                    get.value!!.language,
                    commentStatement
                )
                upload(commentsData)
                }
        }

    }

    private suspend fun upload(comments:Comments){
        withContext(Dispatchers.IO){
            authRepository.setCommentData(comments)
        }
    }


    val textToSpeechEngine: TextToSpeech by lazy {
        TextToSpeech(application) {
            if (it == TextToSpeech.SUCCESS) {
                textToSpeechEngine.language = Locale(get.value!!.language)

            }
        }
    }


    fun textToSpeech(str:String){
        _read.value=str
    }

    fun textToSpeechDone(){
        _read.value=null
    }


    fun initial(engine: TextToSpeech, ) = viewModelScope.launch {
        textToSpeechEngine1 = engine
    }

    fun speak(text: String) = viewModelScope.launch{
        textToSpeechEngine1.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
    val allData: MutableLiveData<MutableList<Comments?>>
        get()=authRepository.CommentDataMutableLiveDataList()


}
