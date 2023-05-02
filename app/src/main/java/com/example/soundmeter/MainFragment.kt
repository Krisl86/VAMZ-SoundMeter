package com.example.soundmeter

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ekn.gruzer.gaugelibrary.Range
import com.example.soundmeter.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: VolumeRecorderViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.refreshRateSlider.addOnChangeListener { _, value, _ ->
            viewModel.updateRefreshRate(value)
        }

        setupGauge()
    }

    private fun setupGauge() {
        val gauge = binding.decibelArcGauge

        gauge.setFormatter{ it.toInt().toString() }
        gauge.valueColor = Color.BLACK
        gauge.minValue = 0.0
        gauge.maxValue = 140.0

        val r1 = Range()
        r1.color = Color.parseColor("#0cad4d")
        r1.from = 0.0
        r1.to = 29.0

        val r2 = Range()
        r2.color = Color.parseColor("#a9c437")
        r2.from = 30.0
        r2.to = 59.0

        val r3 = Range()
        r3.color = Color.parseColor("#fdd106")
        r3.from = 60.0
        r3.to = 84.0

        val r4 = Range()
        r4.color = Color.parseColor("#f8a21a")
        r4.from = 85.0
        r4.to = 99.0

        val r5 = Range()
        r5.color = Color.parseColor("#f16820")
        r5.from = 100.0
        r5.to = 119.0

        val r6 = Range()
        r6.color = Color.parseColor("#ec1a23")
        r6.from = 120.0
        r6.to = 140.0

        gauge.addRange(r1)
        gauge.addRange(r2)
        gauge.addRange(r3)
        gauge.addRange(r4)
        gauge.addRange(r5)
        gauge.addRange(r6)
    }
}