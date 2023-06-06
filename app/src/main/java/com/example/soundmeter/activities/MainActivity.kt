package com.example.soundmeter.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.soundmeter.R
import com.example.soundmeter.fragments.HistoryFragment
import com.example.soundmeter.fragments.MainFragment
import com.example.soundmeter.utilities.BundleStateFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    val mainFragment = MainFragment()
    val historyFragment = HistoryFragment()
    var currentFragment: BundleStateFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 1)
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.historyMenuItem -> setFragmentAsCurrent(historyFragment)
                R.id.mainMenuItem -> setFragmentAsCurrent(mainFragment)
                R.id.otherMenuItem -> setFragmentAsCurrent(mainFragment)
            }; true
        }

        bottomNav.menu.findItem(R.id.mainMenuItem).isChecked = true

        currentFragment = mainFragment
    }

    fun setFragmentAsCurrent(fragment: BundleStateFragment) {
        findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .isVisible = true

        currentFragment = fragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, fragment)
            commit()
        }
    }
}