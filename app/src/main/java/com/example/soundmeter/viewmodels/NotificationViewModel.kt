package com.example.soundmeter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationViewModel : ViewModel() {

    private var _maxValue = MutableLiveData(0)
    val maxValue: LiveData<Int>
        get() = _maxValue

    fun updateMaxValue(value: String) {
        if (value.toIntOrNull() != _maxValue.value)
            _maxValue.value = value.toIntOrNull() ?: 0
    }


    private var _minValue = MutableLiveData(0)
    val minValue: LiveData<Int>
        get() = _minValue

    fun updateMinValue(value: String) {
        if (value.toIntOrNull() != _minValue.value)
            _minValue.value = value.toIntOrNull() ?: 0
    }


    private var _averageValue = MutableLiveData(0)
    val averageValue: LiveData<Int>
        get() = _averageValue

    private var _averageBellow = MutableLiveData(false)
    val averageBellow: LiveData<Boolean>
        get() = _averageBellow

    fun updateAverageBellow(value: Boolean) {
        if (value != _averageBellow.value)
            _averageBellow.value = value
    }

    fun updateAverageValue(value: String) {
        if (value.toIntOrNull() != _averageValue.value)
            _averageValue.value = value.toIntOrNull() ?: 0
    }


    private var _currentValue = MutableLiveData(0)
    val currentValue: LiveData<Int>
        get() = _currentValue

    private var _currentBellow = MutableLiveData(false)
    val currentBellow: LiveData<Boolean>
        get() = _currentBellow

    fun updateCurrentBellow(value: Boolean) {
        if (value != _currentBellow.value)
            _currentBellow.value = value
    }

    fun updateCurrentValue(value: String) {
        if (value.toIntOrNull() != _currentValue.value)
            _currentValue.value = value.toIntOrNull() ?: 0
    }
}