package com.example.soundmeter.soundRecording

import android.media.MediaRecorder
import java.io.File
import kotlin.math.log10

/**
 * State of SoundRecorder and it's MediaRecorder
 *
 */
enum class RecorderState {
    RELEASED,
    RECORDING,
    PAUSED,
}

/**
 * Abstraction over the MediaRecorder class,
 * simple recording, provides volume level.
 *
 */
class SoundRecorder {
    private val maxAmplitude = 32767.0 // the actual max possible amplitude for 16-bit audio sample
                                       // as provided by the android media recorder

    private val savePath = "/storage/emulated/0/Download/volumeRecorderOutput.mp3"

    private var saveRecordingToFile = false
    private var state = RecorderState.RELEASED
    private lateinit var recorder: MediaRecorder

    var calibrationOffset = 0

    /**
     * Start recording
     *
     * @param saveRecording save recording to file
     */
    fun start(saveRecording: Boolean) {
        if (state == RecorderState.PAUSED)
            resume()
        if (state == RecorderState.RECORDING)
            return

        saveRecordingToFile = saveRecording

        recorder = MediaRecorder() // after MR is released, new instance has to be created and used

        try {
            recorder.apply {
                setAudioSource(MediaRecorder.AudioSource.UNPROCESSED)
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

    /**
     * Pause recording
     *
     */
    fun pause() {
        if (state == RecorderState.RECORDING) {
            recorder.pause()
            state = RecorderState.PAUSED
        }
    }

    /**
     * Stop recording
     *
     */
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

    /**
     * Calculates and returns decibel noise value - this is based on amplitude ratio.
     *
     * @return
     */
    fun getDecibelValue(): Double {
        val amplitude = recorder.maxAmplitude // despite the name 'maxAmplitude'
        // it is actually the recorded amplitude value
        val partial = (20 * (log10(amplitude / maxAmplitude)))
        return partial + calibrationOffset
    }

    private fun resume() {
        if (state == RecorderState.PAUSED) {
            recorder.resume()
            state = RecorderState.RECORDING
        }
    }
}