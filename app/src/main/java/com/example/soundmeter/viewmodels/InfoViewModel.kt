package com.example.soundmeter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.soundmeter.soundRecording.VolumeRecorder
import com.example.soundmeter.utilities.Event

const val defaultMin = 9999
const val defaultMax = -9999

class InfoViewModel : ViewModel() {

    private val volumeRecorder = VolumeRecorder.Instance

    private var _maxValue = MutableLiveData(defaultMax)
    val maxValue: LiveData<Int>
        get() = _maxValue

    private var _currentValue = MutableLiveData(defaultMin)
    val currentValue: LiveData<Int>
        get() = _currentValue

    private var _averageValue = MutableLiveData(0)
    val averageValue: LiveData<Int>
        get() = _averageValue

    private var _minValue = MutableLiveData(9999)
    val minValue: LiveData<Int>
        get() = _minValue

    private val values = mutableListOf<Int>()

    val maxValueChanged = Event<Int>() // TODO remove these events
    val minValueChanged = Event<Int>()
    val averageValueChanged = Event<Int>()

    init {
        volumeRecorder.decibelsChanged += ::recalculateValues
        volumeRecorder.recordingStopped += { resetValues() }
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

        if (values.count() > 100) { // to limit the size of the list
            for (i in 0..10)
                values.removeAt(i)
        }

        maxValueChanged.invoke(_maxValue.value!!)
        minValueChanged.invoke(_minValue.value!!)
        averageValueChanged.invoke(averageValue.value!!)

    }

    private fun resetValues() {
        _averageValue.postValue(0)
        values.clear()
        _currentValue.postValue(0)
        _minValue.postValue(defaultMin)
        _maxValue.postValue(defaultMax)
    }
}