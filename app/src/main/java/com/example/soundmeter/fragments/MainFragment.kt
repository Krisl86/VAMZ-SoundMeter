package com.example.soundmeter.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.ekn.gruzer.gaugelibrary.Range
import com.example.soundmeter.R
import com.example.soundmeter.databinding.FragmentMainBinding
import com.example.soundmeter.enums.NoiseReference
import com.example.soundmeter.utilities.BundleStateFragment
import com.example.soundmeter.viewmodels.MainViewModel

const val KEY_START_ENABLED = "key_start_enabled"
const val KEY_PAUSE_ENABLED = "key_pause_enabled"
const val KEY_STOP_ENABLED = "key_stop_enabled"
const val KEY_SAVE_ENABLED = "key_save_enabled"
const val KEY_RATE_ENABLED = "key_rate_enabled"

class MainFragment : BundleStateFragment() {

    private var binding: FragmentMainBinding? = null
    private val viewModel: MainViewModel by viewModels()
    override fun saveState() {
        if (binding != null) {
            state.putBoolean(KEY_START_ENABLED, binding!!.startButton.isEnabled)
            state.putBoolean(KEY_PAUSE_ENABLED, binding!!.pauseButton.isEnabled)
            state.putBoolean(KEY_STOP_ENABLED, binding!!.stopButton.isEnabled)
            state.putBoolean(KEY_SAVE_ENABLED, binding!!.saveRecordingCheckBox.isEnabled)
            state.putBoolean(KEY_RATE_ENABLED, binding!!.refreshRateSlider.isEnabled)
        }
    }

    override fun loadState() {
        if (binding != null) {
            binding!!.startButton.isEnabled = state.getBoolean(KEY_START_ENABLED)
            binding!!.pauseButton.isEnabled = state.getBoolean(KEY_PAUSE_ENABLED)
            binding!!.stopButton.isEnabled = state.getBoolean(KEY_STOP_ENABLED)
            binding!!.saveRecordingCheckBox.isEnabled = state.getBoolean(KEY_SAVE_ENABLED)
            binding!!.refreshRateSlider.isEnabled = state.getBoolean(KEY_RATE_ENABLED)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.vm = viewModel
        binding!!.lifecycleOwner = viewLifecycleOwner

        binding!!.refreshRateSlider.addOnChangeListener { _, value, _ ->
            viewModel.updateRefreshRate(value)
        }

        setupGauge()
    }

    private fun setupGauge() {
        val gauge = binding!!.decibelFullGauge

        gauge.setFormatter{ it.toInt().toString() }
        gauge.valueColor = Color.BLACK
        gauge.minValue = 0.0
        gauge.maxValue = 140.0

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