package com.app.starleet.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LactateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScan(scan: LactateScanEntity)

    @Query("SELECT * FROM lactate_scans ORDER BY timestamp DESC")
    fun getAllScans(): Flow<List<LactateScanEntity>>

    @Query("SELECT * FROM lactate_scans ORDER BY timestamp DESC LIMIT 7")
    fun getLast7Scans(): Flow<List<LactateScanEntity>>

    @Query("SELECT * FROM lactate_scans ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestScan(): LactateScanEntity?
}