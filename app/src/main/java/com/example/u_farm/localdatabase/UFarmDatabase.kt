package com.example.u_farm.localdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.u_farm.model.Problem
import com.google.gson.Gson

@Database(entities = [Problems::class], version = 1,exportSchema = false)
@TypeConverters(Convert::class)
abstract class UFarmDatabase: RoomDatabase(){
    abstract val ufarmDatabaseDao:UFarmDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE: UFarmDatabase? = null
        fun getInstance(context: Context):UFarmDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UFarmDatabase::class.java,
                        "ufarm_problem_statement_list"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

class Convert {
    @TypeConverter
    fun fromList(value: Problem): String = Gson().toJson(value)

    @TypeConverter
    fun toList(value: String) = Gson().fromJson(value, Problem::class.java)
}