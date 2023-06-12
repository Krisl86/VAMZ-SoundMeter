package com.example.soundmeter.fragments

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.soundmeter.NOTIFICATION_CHANNEL_ID
import com.example.soundmeter.R
import com.example.soundmeter.activities.MainActivity
import com.example.soundmeter.databinding.FragmentNotificationBinding
import com.example.soundmeter.soundRecording.VolumeRecorder
import com.example.soundmeter.viewmodels.InfoViewModel
import com.example.soundmeter.viewmodels.NotificationViewModel


class NotificationFragment : Fragment() {

    private lateinit var binding: FragmentNotificationBinding
    private val viewModel: NotificationViewModel by viewModels()
    private val infoViewModel: InfoViewModel by viewModels()

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

        binding.maxSwitch.setOnClickListener {
            setMaxNotification(!binding.maxSwitch.isChecked)
        }
    }

    private fun setMaxNotification(cancel: Boolean = false) {
        if (!cancel)
            infoViewModel.maxValueChanged += ::maxNotificationAction
        else
            infoViewModel.maxValueChanged -= ::maxNotificationAction
    }

    private fun setMinNotification(cancel: Boolean = false) {

    }

    private fun setAverageNotification(cancel: Boolean = false) {

    }

    private fun setCurrentNotification(cancel: Boolean = false) {

    }

    private fun generateNotification(text: String): Notification {
        return NotificationCompat.Builder(requireContext(), NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_action_bar_icon)
            .setContentTitle(text)
            .setAutoCancel(true)
            .build()
    }

    private fun maxNotificationAction(value: Int) {
        if (value > viewModel.maxValue.value ?: 9999) {
            val notification = generateNotification("Max value above set threshold!")
            val notificationManager = requireContext()
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1, notification)
        }
    }
}

//    val notificationManager =
//        requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//    notificationManager.notify(1, notification)
//}