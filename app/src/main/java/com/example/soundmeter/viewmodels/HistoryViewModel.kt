package com.example.soundmeter.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.soundmeter.enums.NoiseReference
import com.example.soundmeter.soundRecording.VolumeRecorder
import com.example.soundmeter.utilities.Event
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.util.Timer
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask
import kotlin.math.roundToInt

class HistoryViewModel : ViewModel() {

    private val volumeRecorder = VolumeRecorder.Instance
    private val timer = Timer()

    private var dataset = LineDataSet(mutableListOf(Entry(0f, 0f)), "some data or something idk")
    var lineData = LineData(dataset)

    val dataAdded = Event<Int>()
    var elapsedTime = 0f

//    val data = listOf(Entry(0f, 0f), Entry(1f, 1f), Entry(2f, 2f))
//    val dataset = LineDataSet(data, "Nejake data lol")
//    val linedata = LineData(dataset)

    init {
        volumeRecorder.decibelsChanged += {value -> addData(value)}
        timer.scheduleAtFixedRate(timerTask { elapsedTime += 1000 }, 0, 1000)
    }

    private fun addData(value: Int) {
        val entry = Entry(elapsedTime / 1000, volumeRecorder.decibels.toFloat())
        lineData.addEntry(entry, 0)
        dataAdded?.invoke(0)
    }
}