package com.app.starleet.states

import com.app.starleet.roomdb.LactateScanEntity

data class LactateState(
    val current: LactateScanEntity? = null,
    val history: List<LactateScanEntity> = emptyList(),
    val trend: List<LactateScanEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedDate: Long? = null,
)