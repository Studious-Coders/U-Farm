package com.example.u_farm.localdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.u_farm.model.Problem


@Entity
data class Problems(
    @PrimaryKey
    val problemUid: String,

    @ColumnInfo(name = "problem")
    var problem: Problem,
)
