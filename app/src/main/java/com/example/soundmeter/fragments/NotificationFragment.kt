package com.example.soundmeter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.soundmeter.R
import com.example.soundmeter.databinding.FragmentNotificationBinding
import com.example.soundmeter.viewmodels.NotificationViewModel


class NotificationFragment : Fragment() {

    private lateinit var binding: FragmentNotificationBinding
    private val viewModel: NotificationViewModel by viewModels()
//    private val infoViewModel: InfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.maxValAboveEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.updateMaxValue(text.toString())
        }

        binding.minValAboveEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.updateMinValue(text.toString())
        }

        binding.avgValAboveEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.updateAverageValue(text.toString())
        }

        binding.currentValAboveEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.updateCurrentValue(text.toString())
        }

        binding.avgBellowAboveToggleButton.setOnClickListener {
            viewModel.updateAverageBellow(binding.avgBellowAboveToggleButton.isEnabled)
        }

        binding.currentBellowAboveToggleButton.setOnClickListener {
            viewModel.updateCurrentBellow(binding.currentBellowAboveToggleButton.isEnabled)
        }

    }
}

//    val notificationManager =
//        requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//    notificationManager.notify(1, notification)
//}