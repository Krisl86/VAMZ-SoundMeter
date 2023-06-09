package com.example.soundmeter.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.soundmeter.R
import com.example.soundmeter.activities.MainActivity
import com.example.soundmeter.databinding.FragmentCalibrationBinding
import com.example.soundmeter.viewmodels.CalibrationViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class CalibrationFragment : Fragment() {

    private lateinit var binding: FragmentCalibrationBinding
    private val viewModel: CalibrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calibration, container, false)

        val navigation = (activity as MainActivity).findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navigation.isVisible = false

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.startRecordingButton.setOnClickListener {
            viewModel.startRecording()
        }

        binding.adjustCalibrationSlider.addOnChangeListener { _, value, _ ->
            viewModel.updateCalibrationValue(value.toInt())
        }

        binding.confirmCalibrationButton.setOnClickListener {
            viewModel.confirmCalibration()
            with (activity.getPreferences(Context.MODE_PRIVATE).edit()) {
                putInt(getString(R.string.calibration_key), viewModel.calibrationOffset)
                putBoolean(getString(R.string.first_start_key), false)
                apply()
            }

            activity.findViewById<BottomNavigationView>(R.id.bottomNavigationView).isVisible = true
            activity.supportActionBar?.show()
            activity.setFragment(MainActivity.mainFragment)
        }
    }
}