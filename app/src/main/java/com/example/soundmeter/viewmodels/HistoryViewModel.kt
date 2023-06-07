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

    private val chartLabel = "Volume Information"

    private val volumeRecorder = VolumeRecorder.Instance
    private val timer = Timer()

    private var dataset = LineDataSet(mutableListOf<Entry>(), chartLabel)
    var lineData = LineData(dataset)

    val dataAdded = Event<Int>()
    var elapsedTime = 0f

    init {
        volumeRecorder.decibelsChanged += {value -> addData(value)}
        timer.scheduleAtFixedRate(timerTask { elapsedTime += 1000 }, 0, 1000)

        dataset.lineWidth = 5f
    }

    private fun addData(value: Int) {
        if (value != 0) {
            val entry = Entry(elapsedTime / 1000, volumeRecorder.decibels.toFloat())
            lineData.addEntry(entry, 0)
            dataAdded.invoke(0)
        }
    }
}