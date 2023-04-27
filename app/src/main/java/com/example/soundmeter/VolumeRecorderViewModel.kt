package com.example.soundmeter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Timer
import kotlin.concurrent.timerTask

enum class RecordingState {
    STARTED,
    PAUSED,
    STOPPED
}

class VolumeRecorderViewModel : ViewModel() {

    private var saveToFile = false
    private val recorder = VolumeRecorder()

    private var timer = Timer()

    private var _decibels = MutableLiveData(0.0)
    val decibels: LiveData<Double>
        get() = _decibels

    private var recordingState = RecordingState.STOPPED
        set(value) {
            field = value
            when (value) {
                RecordingState.STARTED -> { _startEnabled.value = false; _pauseEnabled.value = true;
                    _stopEnabled.value = true; _saveToFileEnabled.value = false}
                RecordingState.PAUSED -> { _startEnabled.value = true; _pauseEnabled.value = false;
                    _stopEnabled.value = true; _saveToFileEnabled.value = false}
                RecordingState.STOPPED -> { _startEnabled.value = true; _pauseEnabled.value = false;
                    _stopEnabled.value = false; _saveToFileEnabled.value = true}
            }
        }

    private var _startEnabled = MutableLiveData(true);
    val startEnabled: LiveData<Boolean>
        get() = _startEnabled

    private var _pauseEnabled = MutableLiveData(false);
    val pauseEnabled: LiveData<Boolean>
        get() = _pauseEnabled

    private var _stopEnabled = MutableLiveData(false);
    val stopEnabled: LiveData<Boolean>
        get() = _stopEnabled

    private var _saveToFileEnabled = MutableLiveData(true);
    val saveToFileEnabled: LiveData<Boolean>
        get() = _saveToFileEnabled

    fun switchRecording() {
        if (recordingState == RecordingState.STARTED) {
            pauseRecording()
            recordingState = RecordingState.PAUSED
        }
        else if (recordingState == RecordingState.PAUSED || recordingState == RecordingState.STOPPED) {
            startRecording()
            recordingState = RecordingState.STARTED
        }
    }

    fun stopRecording() {
        if (recordingState == RecordingState.STARTED || recordingState == RecordingState.PAUSED) {
            recorder.stop()
            _decibels.value = 0.0
            timer.cancel()
            recordingState = RecordingState.STOPPED
        }
    }

    fun switchSaveToFile() {
        saveToFile = !saveToFile
    }

    private fun startRecording() {
        recorder.start(saveToFile)

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

        recordingState = RecordingState.STARTED
    }

    private fun pauseRecording() {
        recorder.pause()
        timer.cancel()
        recordingState = RecordingState.PAUSED
    }
}