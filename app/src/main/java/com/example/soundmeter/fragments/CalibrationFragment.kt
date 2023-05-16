package com.example.soundmeter.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.soundmeter.R
import com.example.soundmeter.databinding.FragmentCalibrationBinding
import com.example.soundmeter.viewmodels.CalibrationViewModel

class CalibrationFragment : Fragment() {

    private lateinit var binding: FragmentCalibrationBinding
    private val viewModel: CalibrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calibration, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.adjustCalibrationSlider.addOnChangeListener { _, value, _ ->
            viewModel.updateCalibrationValue(value.toInt())
        }

        binding.confirmCalibrationButton.setOnClickListener {
            viewModel.confirmCalibration()
        }
    }
}