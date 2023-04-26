package com.example.soundmeter

import android.media.MediaRecorder
import java.io.IOException
import kotlin.math.log10

enum class RecorderState {
    RELEASED,
    RECORDING,
    PAUSED,
}

class VolumeRecorder {

    private var recorder = MediaRecorder()
    private val fileName = "/storage/emulated/0/Download/hello.mp3"
    private val directory = ""
    private val calibrationOffset = 60

    private var state = RecorderState.RELEASED

    fun start() {
        if (state == RecorderState.PAUSED)
            resume()
        if (state == RecorderState.RECORDING)
            return

        recorder.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(fileName)

            try {
                prepare()
            } catch (e: IOException) {

            }

            start()
            state = RecorderState.RECORDING
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