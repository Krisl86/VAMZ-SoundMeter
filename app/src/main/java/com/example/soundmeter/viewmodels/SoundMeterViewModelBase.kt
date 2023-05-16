package com.example.soundmeter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.soundmeter.soundRecording.VolumeRecorder

abstract class SoundMeterViewModelBase : ViewModel() {

    protected val volumeRecorder = VolumeRecorder()

    protected var _decibels = MutableLiveData(0)
    val decibels: LiveData<Int>
        get() = _decibels

    init {
        volumeRecorder.decibelsChanged += { value -> _decibels.postValue(value) }
    }

    fun switchRecording() {
        volumeRecorder.switchRecording()
    }

    fun stopRecording() {
        volumeRecorder.stopRecording()
    }
}