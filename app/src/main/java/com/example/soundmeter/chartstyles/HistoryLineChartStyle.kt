package com.example.soundmeter.chartstyles

import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis

class HistoryLineChartStyle {

    fun style(chart: LineChart) {
        chart.apply {
            setDrawGridBackground(true)
            axisRight.isEnabled = false

            axisLeft.apply {
                axisMinimum = 20f
                axisMaximum = 100f
                spaceTop = 10f
            }

            xAxis.apply {
                isGranularityEnabled = false
                setDrawGridLines(false)
                setDrawAxisLine(false)
                position = XAxis.XAxisPosition.BOTTOM
            }

            setScaleEnabled(false)
            setPinchZoom(false)
            description = null
        }
    }
}