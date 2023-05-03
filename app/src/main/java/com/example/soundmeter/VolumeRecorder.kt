package com.example.soundmeter

import android.media.MediaRecorder
import java.io.File
import java.io.IOException
import kotlin.math.log10

enum class RecorderState {
    RELEASED,
    RECORDING,
    PAUSED,
}

class VolumeRecorder {

    private val savePath = "/storage/emulated/0/Download/hello.mp3"
    private val calibrationOffset = 90

    private var saveRecordingToFile = false
    private var state = RecorderState.RELEASED
    private lateinit var recorder: MediaRecorder

    fun start(saveRecording: Boolean) {
        if (state == RecorderState.PAUSED)
            resume()
        if (state == RecorderState.RECORDING)
            return

        saveRecordingToFile = saveRecording

        recorder = MediaRecorder() // after MR is released, new instance has to be created and used

        try {
            recorder.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(savePath)

                prepare()
                start()
                state = RecorderState.RECORDING
            }
        } catch (e: Exception) {
            e.printStackTrace()
            recorder.release()
        }
    }

    fun pause() {
        if (state == RecorderState.RECORDING) {
            recorder.pause()
            state = RecorderState.PAUSED
        }
    }

    fun stop() {
        if (state == RecorderState.PAUSED || state == RecorderState.RECORDING) {
            recorder.stop()
            recorder.release()
            state = RecorderState.RELEASED

            if (!saveRecordingToFile) {
                val file = File(savePath)
                file.delete()
            }
        }
    }

    fun getDecibelValue(): Double {
        val amplitude = recorder.maxAmplitude
        return (20 * (log10(amplitude / 32767.0))) + calibrationOffset
    }

    private fun resume() {
        if (state == RecorderState.PAUSED) {
            recorder.resume()
            state = RecorderState.RECORDING
        }
    }
}