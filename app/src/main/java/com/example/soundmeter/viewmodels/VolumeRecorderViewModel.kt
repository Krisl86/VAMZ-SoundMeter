package com.example.soundmeter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.soundmeter.VolumeRecorder
import com.example.soundmeter.enums.NoiseReference
import java.util.Timer
import kotlin.concurrent.timerTask
import kotlin.math.roundToInt

enum class RecordingState {
    STARTED,
    PAUSED,
    STOPPED
}

class VolumeRecorderViewModel : ViewModel() {

    private var saveToFile = false
    private val recorder = VolumeRecorder()

    private var timer = Timer()

    private var _decibels = MutableLiveData(0)
    val decibels: LiveData<Int>
        get() = _decibels

    private var _noiseRefString = MutableLiveData(NoiseReference.WHISPER.description)
    val noiseRefString: LiveData<String>
        get() = _noiseRefString

    private var recordingState = RecordingState.STOPPED
        set(value) {
            field = value
            when (value) {
                RecordingState.STARTED -> { _startEnabled.value = false; _pauseEnabled.value = true
                    _stopEnabled.value = true; _saveToFileEnabled.value = false
                    _refreshRateEnabled.value = false}
                RecordingState.PAUSED -> { _startEnabled.value = true; _pauseEnabled.value = false
                    _stopEnabled.value = true; _saveToFileEnabled.value = false
                    _refreshRateEnabled.value = false}
                RecordingState.STOPPED -> { _startEnabled.value = true; _pauseEnabled.value = false
                    _stopEnabled.value = false; _saveToFileEnabled.value = true
                    _refreshRateEnabled.value = true}
            }
        }

    val minRefreshRate = 0.5f
    val maxRefreshRate = 5f

    val refreshRate = MutableLiveData(0.5f)

    fun updateRefreshRate(value: Float) {
        if (refreshRate.value != value)
            refreshRate.value = value
    }

    private var _startEnabled = MutableLiveData(true)
    val startEnabled: LiveData<Boolean>
        get() = _startEnabled

    private var _pauseEnabled = MutableLiveData(false)
    val pauseEnabled: LiveData<Boolean>
        get() = _pauseEnabled

    private var _stopEnabled = MutableLiveData(false)
    val stopEnabled: LiveData<Boolean>
        get() = _stopEnabled

    private var _saveToFileEnabled = MutableLiveData(true)
    val saveToFileEnabled: LiveData<Boolean>
        get() = _saveToFileEnabled

    private var _refreshRateEnabled = MutableLiveData(true)
    val refreshRateEnabled: LiveData<Boolean>
        get() = _refreshRateEnabled

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
            _decibels.value = 0
            _noiseRefString.value = NoiseReference.stringByValue(0.0)
            timer.cancel()
            recordingState = RecordingState.STOPPED
        }
    }

    fun switchSaveToFile() {
        saveToFile = !saveToFile
    }

    private fun startRecording() {
        recorder.start(saveToFile)

        val rate = ((refreshRate.value ?: 0.5f) * 1000).toLong()

        timer = Timer() // has to be reassigned after timer.cancel()
        timer.scheduleAtFixedRate(
            timerTask {
                try {
                    var recordedValue = recorder.getDecibelValue().roundToInt()
                    if (recordedValue < -1000)
                        recordedValue = 0

                    _decibels.postValue(recordedValue)
                    _noiseRefString.postValue(NoiseReference.stringByValue(recordedValue.toDouble()))
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }

            }, 0, rate
        )

        recordingState = RecordingState.STARTED
    }

    private fun pauseRecording() {
        recorder.pause()
        timer.cancel()
        recordingState = RecordingState.PAUSED
    }
}