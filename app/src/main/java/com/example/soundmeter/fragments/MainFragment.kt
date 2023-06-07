package com.example.soundmeter.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ekn.gruzer.gaugelibrary.Range
import com.example.soundmeter.R
import com.example.soundmeter.databinding.FragmentMainBinding
import com.example.soundmeter.enums.NoiseReference
import com.example.soundmeter.viewmodels.MainViewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

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
        val gauge = binding.decibelFullGauge

        gauge.setFormatter{ it.toInt().toString() }
        gauge.valueColor = Color.BLACK
        gauge.minValue = 0.0
        gauge.maxValue = 100.0

        val r1 = Range()
        r1.color = Color.parseColor("#0cad4d")
        r1.from = NoiseReference.WHISPER.minValue
        r1.to = NoiseReference.WHISPER.maxValue

        val r2 = Range()
        r2.color = Color.parseColor("#6eb646")
        r2.from = NoiseReference.HOME.minValue
        r2.to = NoiseReference.HOME.maxValue

        val r3 = Range()
        r3.color = Color.parseColor("#a9c437")
        r3.from = NoiseReference.CONVERSATION.minValue
        r3.to = NoiseReference.CONVERSATION.maxValue

        val r4 = Range()
        r4.color = Color.parseColor("#fcde00")
        r4.from = NoiseReference.VACUUM.minValue
        r4.to = NoiseReference.VACUUM.maxValue

        val r5 = Range()
        r5.color = Color.parseColor("#fdad18")
        r5.from = NoiseReference.MOTORCYCLE.minValue
        r5.to = NoiseReference.MOTORCYCLE.maxValue

        val r6 = Range()
        r6.color = Color.parseColor("#f5891c")
        r6.from = NoiseReference.CHAINSAW.minValue
        r6.to = NoiseReference.CHAINSAW.maxValue

        val r7 = Range()
        r7.color = Color.parseColor("#f16820")
        r7.from = NoiseReference.CONCERT.minValue
        r7.to = NoiseReference.CONCERT.maxValue

        val r8 = Range()
        r8.color = Color.parseColor("#ec1a23")
        r8.from = NoiseReference.GUN.minValue
        r8.to = NoiseReference.GUN.maxValue

        gauge.addRange(r1)
        gauge.addRange(r2)
        gauge.addRange(r3)
        gauge.addRange(r4)
        gauge.addRange(r5)
        gauge.addRange(r6)
        gauge.addRange(r7)
        gauge.addRange(r8)
    }
}