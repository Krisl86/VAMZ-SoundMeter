package com.example.soundmeter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.soundmeter.R
import com.example.soundmeter.databinding.FragmentHistoryBinding
import com.example.soundmeter.viewmodels.HistoryViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineDataSet

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val viewModel: HistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupLineStyle(viewModel.lineDataSet)
        setupChart()
    }

    private fun setupChart() {
        val chart = binding.testChart
        if (chart != null) {
            chart.data = viewModel.lineData

            viewModel.dataAdded += {
                chart.xAxis.apply {
                    axisMinimum = ((viewModel.elapsedTime / 1000).toInt() - 10).toFloat()
                    axisMaximum = ((viewModel.elapsedTime / 1000).toInt() + 10).toFloat()
                }
                chart.notifyDataSetChanged()
                chart.invalidate()
            }

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

    private fun setupLineStyle(lineDataSet: LineDataSet) {
        lineDataSet.apply {
            setDrawValues(false)
            lineWidth = 6f
            isHighlightEnabled = false
            setDrawHighlightIndicators(false)
            setDrawCircles(false)
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            setDrawFilled(true)
            fillDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.bg_chart_line)
        }
    }
}