package com.app.starleet.roomdb

import kotlinx.coroutines.flow.Flow

class LactateRepository(
    private val dao: LactateDao
) {

    val allScans: Flow<List<LactateScanEntity>> =
        dao.getAllScans()

    val last7Scans: Flow<List<LactateScanEntity>> =
        dao.getLast7Scans()

    suspend fun insertScan(
        value: Double,
        sweat: String,
        change: Double
    ) {
        dao.insertScan(
            LactateScanEntity(
                lactateValue = value,
                sweatStatus = sweat,
                changeFromLast = change,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    suspend fun getLatest() = dao.getLatestScan()
}