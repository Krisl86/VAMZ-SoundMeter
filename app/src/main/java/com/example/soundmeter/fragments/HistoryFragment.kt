package com.example.soundmeter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.soundmeter.R
import com.example.soundmeter.databinding.FragmentHistoryBinding
import com.example.soundmeter.utilities.BundleStateFragment
import com.example.soundmeter.viewmodels.HistoryViewModel

class HistoryFragment : BundleStateFragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val viewModel: HistoryViewModel by viewModels()

    override fun saveState() {
    }

    override fun loadState() {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.dataAdded += {
            binding.testChart.data = viewModel.lineData
            binding.testChart.invalidate()}
    }

}