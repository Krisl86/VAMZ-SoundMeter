package com.example.soundmeter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.soundmeter.soundRecording.VolumeRecorder

class InfoViewModel : ViewModel() {

    private val volumeRecorder = VolumeRecorder.Instance

    private var _maxValue = MutableLiveData(-9999)
    val maxValue: LiveData<Int>
        get() = _maxValue

    private var _currentValue = MutableLiveData(0)
    val currentValue: LiveData<Int>
        get() = _currentValue

    private var _averageValue = MutableLiveData(0)
    val averageValue: LiveData<Int>
        get() = _averageValue

    private var _minValue = MutableLiveData(9999)
    val minValue: LiveData<Int>
        get() = _minValue

    private val values = mutableListOf<Int>()

    init {
        volumeRecorder.decibelsChanged += {value -> recalculateValues(value) }
    }

    private fun recalculateValues(value: Int) {
        _currentValue.postValue(value)

        if (value != 0) {
            if (value < _minValue.value!!)
                _minValue.postValue(value)
            if (value > _maxValue.value!!)
                _maxValue.postValue(value)
        }

        values.add(value)
        _averageValue.postValue(values.average().toInt())
    }
}