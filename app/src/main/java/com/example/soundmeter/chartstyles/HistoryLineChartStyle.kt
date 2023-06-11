package com.example.soundmeter.chartstyles

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.soundmeter.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineDataSet

class HistoryLineChartStyle(val context: Context) {

    fun styleChart(chart: LineChart) {
        chart.apply {
            setDrawGridBackground(true)
            axisRight.isEnabled = false

            axisLeft.apply {
                axisMinimum = 20f
                axisMaximum = 120f
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

    fun styleLine(lineDataSet: LineDataSet) {
        lineDataSet.apply {
            setDrawValues(false)
            lineWidth = 6f
            isHighlightEnabled = false
            setDrawHighlightIndicators(false)
            setDrawCircles(false)
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            setDrawFilled(true)
            fillDrawable = ContextCompat.getDrawable(context, R.drawable.bg_chart_line)
        }
    }
}