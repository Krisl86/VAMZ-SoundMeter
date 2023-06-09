package com.example.soundmeter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.soundmeter.R
import com.example.soundmeter.chartstyles.HistoryLineChartStyle
import com.example.soundmeter.databinding.FragmentHistoryBinding
import com.example.soundmeter.viewmodels.HistoryViewModel

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

        setupChart()
    }

    private fun setupChart() {
        val chart = binding.testChart
        if (chart != null) {
            chart.data = viewModel.lineData

            viewModel.dataAdded += {
                chart.xAxis.apply {
                    axisMinimum = if (viewModel.elapsedTime / 1000 < 10)
                        1f
                    else
                        ((viewModel.elapsedTime / 1000).toInt() - 10).toFloat()

                    axisMaximum = ((viewModel.elapsedTime / 1000).toInt() + 8).toFloat()
                }
                chart.notifyDataSetChanged()
                chart.invalidate()
            }

            val style = HistoryLineChartStyle(requireContext())
            style.styleChart(chart)
            style.styleLine(viewModel.lineDataSet)
        }
    }
}