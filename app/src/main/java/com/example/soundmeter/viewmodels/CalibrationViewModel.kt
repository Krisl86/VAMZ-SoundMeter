package com.example.soundmeter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CalibrationViewModel : SoundMeterViewModelBase() {

    private var _startRecordingEnabled = MutableLiveData(true)
    val startRecordingEnabled: LiveData<Boolean>
        get() = _startRecordingEnabled

    var calibrationOffset: Int
        get() = volumeRecorder.calibrationOffset
        set(value) {
            volumeRecorder.calibrationOffset = value
        }

    fun startRecording() {
        switchRecording()
        _startRecordingEnabled.value = false
    }

    fun updateCalibrationValue(value: Int) {
        calibrationOffset = value
    }

    fun confirmCalibration() {
        stopRecording()
    }
}