package com.example.soundmeter.utilities

import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class BundleStateFragment : Fragment() {
    var state: Bundle = Bundle()

    abstract fun saveState()
    abstract fun loadState()
}