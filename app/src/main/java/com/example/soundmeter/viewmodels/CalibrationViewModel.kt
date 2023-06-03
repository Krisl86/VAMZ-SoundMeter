package com.example.soundmeter.viewmodels

class CalibrationViewModel : SoundMeterViewModelBase() {
    init {
        switchRecording()
    }

    var calibrationOffset: Int
        get() = volumeRecorder.calibrationOffset
        set(value) {
            volumeRecorder.calibrationOffset = value
        }

    fun updateCalibrationValue(value: Int) {
        calibrationOffset = value
    }

    fun confirmCalibration() {
        stopRecording()

    }
}