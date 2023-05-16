package com.example.soundmeter.viewmodels

class CalibrationViewModel : SoundMeterViewModelBase() {
    init {
        switchRecording()
    }

    fun updateCalibrationValue(value: Int) {
        volumeRecorder.calibrationOffset = value
    }

    fun confirmCalibration() {
        stopRecording()
    }
}