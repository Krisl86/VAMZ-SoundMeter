package com.example.soundmeter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.soundmeter.enums.NoiseReference
import com.example.soundmeter.soundRecording.RecordingState
import com.example.soundmeter.soundRecording.VolumeRecorder

class MainViewModel : SoundMeterViewModelBase() {

    private var _noiseRefString = MutableLiveData("")
    val noiseRefString: LiveData<String>
        get() = _noiseRefString

    init {
        volumeRecorder.noiseRefStringChanged += { value -> _noiseRefString.postValue(value) }
        volumeRecorder.recordingStateChanged += ::updateVisibilities
    }

    val minRefreshRate = VolumeRecorder.defaultRefreshRate
    val maxRefreshRate = 5f

    val refreshRate = MutableLiveData(VolumeRecorder.defaultRefreshRate)

    fun updateRefreshRate(value: Float) {
        if (volumeRecorder.refreshRate != value) {
            volumeRecorder.refreshRate = value
            refreshRate.value = value
        }
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

    fun switchSaveToFile() {
        volumeRecorder.saveToFile = !volumeRecorder.saveToFile
    }

    private fun updateVisibilities(value: RecordingState) {
        when (value) {
            RecordingState.STARTED -> {
                _startEnabled.value = false; _pauseEnabled.value = true
                _stopEnabled.value = true; _saveToFileEnabled.value = false
                _refreshRateEnabled.value = false
            }
            RecordingState.PAUSED -> {
                _startEnabled.value = true; _pauseEnabled.value = false
                _stopEnabled.value = true; _saveToFileEnabled.value = false
                _refreshRateEnabled.value = false
            }
            RecordingState.STOPPED -> {
                _startEnabled.value = true; _pauseEnabled.value = false
                _stopEnabled.value = false; _saveToFileEnabled.value = true
                _refreshRateEnabled.value = true
            }
        }
    }
}