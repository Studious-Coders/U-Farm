package com.example.u_farm.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.u_farm.network.News
import com.example.u_farm.network.NewsApi
import com.example.u_farm.network.NewsConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.await

class NewsViewModel(application: Application): ViewModel() {

    private val _newsFeed = MutableLiveData<List<News>>()
    val newsFeed: LiveData<List<News>>
        get() = _newsFeed


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init{
        getHomeFeed()
    }
    fun getHomeFeed(){
        coroutineScope.launch {
            val getPropertiesDeferred = NewsApi.retrofitService.getNews( NewsConstants.api_key, NewsConstants.field)

            try {
                val listResult = getPropertiesDeferred.await()
                Log.d("Api Data",listResult.toString())
                val trendList=getnews(listResult.results)

                _newsFeed.value = trendList

            }catch(e:Exception){
                Log.d("Exception","${e}")
            }
        }
    }

    fun getnews(l:List<News>):List<News>{
        val localMovies:MutableList<News> = mutableListOf()
        var i=0
        l.forEach {
                if(i <= 2) {
                    localMovies.add(News(it.title, it.link, it.desc, it.image_url))
                    i++
                }
        }

        return localMovies
    }
}