package com.example.u_farm.network

import android.os.Parcelable
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.parcel.Parcelize
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://newsdata.io"

private val moshi= Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit= Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()
interface NewsApiService{
    @GET("/api/1/news")
    fun getNews(@Query("apikey") api_key : String, @Query("q") q : String) : Call<NewsListProperty>
}
object NewsApi{
    val retrofitService:NewsApiService by lazy{
        retrofit.create(NewsApiService::class.java)
    }
}
data class NewsListProperty(val results: List<News>)
@Parcelize
data class News(
    val title:String?,
    val link:String?,
    val description:String?,
    val image_url:String?,
): Parcelable{
    val desc :String
        get() = description!!.substring(0, Math.min(description.length,10)) + "..."
}

class NewsConstants{
    companion object{
        const val api_key = "pub_213032277d5fc9a185d8fd3657596d0d7ff73"
        const val field = "agri"
        const val country = "india"
    }
}