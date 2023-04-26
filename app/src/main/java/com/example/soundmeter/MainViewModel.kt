package com.example.soundmeter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Timer
import kotlin.concurrent.timerTask

class MainViewModel() : ViewModel() {

    private var timer = Timer()
    private val recorder = VolumeRecorder()

    var isRecording = false

    private var _isRecordingText = MutableLiveData("Start")
    val isRecordingText: LiveData<String>
        get() = _isRecordingText

    private var _decibels = MutableLiveData(0.0)
    val decibels: LiveData<Double>
        get() = _decibels

    var _isStopped = MutableLiveData(true)
    val isStopped: LiveData<Boolean>
        get() = _isStopped

    fun switchRecording() {
        isRecording = !isRecording
        if (isRecording)
            startRecording()
        else
            pauseRecording()
    }

    fun stopRecording() {
        if (isStopped.value == false) {
            isRecording = false
            _isRecordingText.value = "Start"
            recorder.stop()
            _isStopped.value = true
            timer.cancel()
        }
    }

    private fun startRecording() {
        recorder.start()
        _isRecordingText.value = "Pause"
        _isStopped.value = false

        timer = Timer() // has to be reassigned after timer.cancel()
        timer.scheduleAtFixedRate(
            timerTask {
                try {
                    _decibels.postValue(recorder.getDecibelValue())
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }

            }, 250, 250
        )
    }

    private fun pauseRecording() {
        recorder.pause()
        _isRecordingText.value = "Start"
        timer.cancel()
    }
}