package com.example.soundmeter.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.soundmeter.R
import com.example.soundmeter.databinding.FragmentHistoryBinding
import com.example.soundmeter.databinding.FragmentInfoBinding
import com.example.soundmeter.viewmodels.HistoryViewModel
import com.example.soundmeter.viewmodels.InfoViewModel

class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding
    private val viewModel: InfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        return binding.root
    }
}