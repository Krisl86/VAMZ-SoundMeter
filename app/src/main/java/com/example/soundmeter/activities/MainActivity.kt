package com.example.soundmeter.activities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.soundmeter.R
import com.example.soundmeter.fragments.CalibrationFragment
import com.example.soundmeter.fragments.HistoryFragment
import com.example.soundmeter.fragments.InfoFragment
import com.example.soundmeter.fragments.MainFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    val mainFragment = MainFragment()
    private val historyFragment = HistoryFragment()
    private val calibrationFragment = CalibrationFragment()
    private val infoFragment = InfoFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermissions()
        initBottomNavigation()
        initStartFragment()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }
    }

    private fun initBottomNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.historyMenuItem -> replaceFragment(historyFragment)
                R.id.mainMenuItem -> replaceFragment(mainFragment)
                R.id.infoMenuItem -> replaceFragment(infoFragment)
            }; true
        }

        bottomNav.menu.findItem(R.id.mainMenuItem).isChecked = true
    }

    private fun initStartFragment() {
        val preferences = getPreferences(Context.MODE_PRIVATE)
        if (preferences!!.getBoolean(getString(R.string.first_start_key), true)) {
            replaceFragment(calibrationFragment)
            findViewById<BottomNavigationView>(R.id.bottomNavigationView).isVisible = false
        }
        else
            replaceFragment(mainFragment)
    }
}