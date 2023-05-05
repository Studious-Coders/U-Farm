package com.example.u_farm.localdatabase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UFarmDatabaseDao {
    @Query("select * from Problems")
    fun getProblem(): LiveData<List<Problems>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllProblems(problems: List<Problems?>)
}