package com.example.soundmeter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CalibrationViewModel : SoundMeterViewModelBase() {

    private var _startRecordingEnabled = MutableLiveData(true)
    val startRecordingEnabled: LiveData<Boolean>
        get() = _startRecordingEnabled

    private var _confirmEnabled = MutableLiveData(false)
    val confirmEnabled: LiveData<Boolean>
        get() = _confirmEnabled

    var calibrationOffset: Int
        get() = volumeRecorder.calibrationOffset
        set(value) {
            volumeRecorder.calibrationOffset = value
        }

    fun startRecording() {
        switchRecording()
        _startRecordingEnabled.value = false
        _confirmEnabled.value = true
    }

    fun updateCalibrationValue(value: Int) {
        calibrationOffset = value
    }

    fun confirmCalibration() {
        stopRecording()
    }
}