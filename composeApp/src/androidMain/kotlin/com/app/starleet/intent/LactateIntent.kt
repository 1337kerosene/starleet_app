package com.app.starleet.intent

sealed class LactateIntent {

    data class AddScan(
        val value: Double,
        val sweat: String,
        val change: Double
    ) : LactateIntent()
    data class SelectHistoryDate(val dateMillis: Long) : LactateIntent()
    object LoadLactate : LactateIntent()
}