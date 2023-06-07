package com.example.soundmeter.soundRecording

import com.example.soundmeter.utilities.Event
import com.example.soundmeter.enums.NoiseReference
import java.util.Timer
import kotlin.concurrent.timerTask
import kotlin.math.roundToInt

enum class RecordingState {
    STARTED,
    PAUSED,
    STOPPED
}

class VolumeRecorder {

    companion object {
        const val defaultRefreshRate = 0.5f
        const val defaultCalibrationOffset = 80

        var calibrationOffset = 0

        private val instance = VolumeRecorder()
        val Instance
            get() = instance
    }

    private val recorder = SoundRecorder()
    private var timer = Timer()

    private var noiseRefString = ""
        set(value) {
            field = value
            noiseRefStringChanged.invoke(value)
        }

    private var recordingState = RecordingState.STOPPED
        private set(value) {
            field = value
            recordingStateChanged.invoke(value)
        }

    var saveToFile = false
    var refreshRate = 0.5f

    var calibrationOffset: Int
        get() = recorder.calibrationOffset
        set(value) {
            recorder.calibrationOffset = value
            VolumeRecorder.calibrationOffset = value
        }

    var decibels = 0
        private set(value) {
            field = value
            decibelsChanged.invoke(value)
        }

    val recordingStateChanged = Event<RecordingState>()
    val noiseRefStringChanged = Event<String>()
    val decibelsChanged = Event<Int>()
    val recordingStopped = Event<Int>()

    init {
        calibrationOffset = VolumeRecorder.calibrationOffset
    }

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
            noiseRefString = NoiseReference.stringByValue(0.0)
            timer.cancel()
            recordingState = RecordingState.STOPPED
            recordingStopped.invoke(decibels)
            decibels = 0
        }
    }

    private fun startRecording() {
        recorder.start(saveToFile)

        val rate = (refreshRate * 1000).toLong()

        timer = Timer() // has to be reassigned after timer.cancel()
        timer.scheduleAtFixedRate(
            timerTask {
                try {
                    var recordedValue = recorder.getDecibelValue().roundToInt()
                    if (recordedValue < -1000)
                        recordedValue = 0

                    decibels = recordedValue
                    noiseRefString = NoiseReference.stringByValue(recordedValue.toDouble())
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