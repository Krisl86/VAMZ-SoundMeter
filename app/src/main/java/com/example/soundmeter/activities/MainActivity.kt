package com.example.soundmeter.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.soundmeter.R
import com.example.soundmeter.fragments.HistoryFragment
import com.example.soundmeter.fragments.MainFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 1)
        }

        val mainFrag = MainFragment()
        val historyFrag = HistoryFragment()

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.historyMenuItem -> setFragmentAsCurrent(historyFrag)
                R.id.mainMenuItem -> setFragmentAsCurrent(mainFrag)
                R.id.otherMenuItem -> setFragmentAsCurrent(mainFrag)
            }; true
        }

        bottomNav.menu.findItem(R.id.mainMenuItem).isChecked = true
    }

    private fun setFragmentAsCurrent(frag: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, frag)
            commit()
        }
    }
}