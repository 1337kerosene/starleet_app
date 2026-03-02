package com.app.starleet.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.app.starleet.intent.LactateIntent
import com.app.starleet.roomdb.LactateRepository
import com.app.starleet.states.LactateState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LactateViewModel(
    private val repository: LactateRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(LactateState())
    val state: StateFlow<LactateState> = _state

    init {
        processIntent(LactateIntent.LoadLactate)
    }

    fun processIntent(intent: LactateIntent) {
        when (intent) {

            is LactateIntent.LoadLactate -> loadLactate()

            is LactateIntent.AddScan -> addScan(
                intent.value,
                intent.sweat,
                intent.change
            )

            is LactateIntent.SelectHistoryDate -> {
                _state.update { it.copy(selectedDate = intent.dateMillis) }
            }
        }
    }

    private fun loadLactate() {

        viewModelScope.launch {

            repository.allScans.collect { allData ->

                val selectedDate = _state.value.selectedDate

                val filteredHistory = if (selectedDate != null) {

                    val start = getStartOfDay(selectedDate)
                    val end = start + (24 * 60 * 60 * 1000)

                    allData.filter {
                        it.timestamp in start until end
                    }

                } else {
                    allData
                }

                _state.update {
                    it.copy(
                        history = filteredHistory,
                        trend = allData.takeLast(7)
                    )
                }
            }
        }

        viewModelScope.launch {
            val latest = repository.getLatest()
            _state.update { it.copy(current = latest) }
        }
    }

    private fun addScan(
        value: Double,
        sweat: String,
        change: Double
    ) {
        viewModelScope.launch {


            val randomValue = (value + (0..10).random() / 10.0)
                .coerceIn(0.0, 4.0)
                .roundTo1Decimal()

            val randomChange = ((-5..5).random() / 10.0)
                .roundTo1Decimal()

            repository.insertScan(
                value = randomValue,
                sweat = sweat,
                change = randomChange
            )


            val latest = repository.getLatest()

            _state.update {
                it.copy(current = latest)
            }
        }
    }

    private fun getStartOfDay(time: Long): Long {
        val cal = java.util.Calendar.getInstance()
        cal.timeInMillis = time
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0)
        cal.set(java.util.Calendar.MINUTE, 0)
        cal.set(java.util.Calendar.SECOND, 0)
        cal.set(java.util.Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }

    fun Double.roundTo1Decimal(): Double {
        return String.format("%.1f", this).toDouble()
    }
}