package com.app.starleet.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lactate_scans")
data class LactateScanEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val lactateValue: Double,
    val sweatStatus: String,
    val changeFromLast: Double,
    val timestamp: Long
)