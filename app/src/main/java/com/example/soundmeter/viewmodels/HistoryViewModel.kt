package com.example.soundmeter.viewmodels

import androidx.lifecycle.ViewModel
import com.example.soundmeter.soundRecording.VolumeRecorder
import com.example.soundmeter.utilities.Event
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.util.Timer
import kotlin.concurrent.timerTask

class HistoryViewModel : ViewModel() {

    private val chartLabel = "Volume (db)"

    private val volumeRecorder = VolumeRecorder.Instance
    private val timer = Timer()

    var lineDataSet = LineDataSet(mutableListOf<Entry>(), chartLabel)
    var lineData = LineData(lineDataSet)

    val dataAdded = Event<Int>()
    var elapsedTime = 0f

    init {
        volumeRecorder.decibelsChanged += {value -> addData(value)}
        timer.scheduleAtFixedRate(timerTask { elapsedTime += 100 }, 0, 100)
    }

    private fun addData(value: Int) {
        if (value != 0) {
            val entry = Entry(elapsedTime / 1000, volumeRecorder.decibels.toFloat())
            lineData.addEntry(entry, 0)
            dataAdded.invoke(0)
        }
    }
}