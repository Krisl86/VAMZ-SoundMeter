package com.example.soundmeter.activities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import com.example.soundmeter.fragments.NotificationFragment
import com.example.soundmeter.soundRecording.RecordingState
import com.example.soundmeter.soundRecording.VolumeRecorder
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    companion object {
        private var currentFragment: Fragment? = null

        val mainFragment = MainFragment()
        private val historyFragment = HistoryFragment()
        private val calibrationFragment = CalibrationFragment()
        private val infoFragment = InfoFragment()
//        private val notificationFragment = NotificationFragment()
    }

    private var startActionBarMenuItem: MenuItem? = null
    private var pauseActionBarMenuItem: MenuItem? = null
    private var stopActionBarMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.appToolbar))
        VolumeRecorder.Instance.recordingStateChanged += ::updateActionBarItemVisibilities

        checkPermissions()
        initVolumeRecorderOffset()
        initBottomNavigation()
        initStartFragment()
    }

    fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()

        supportActionBar?.title = titleByFragment(fragment)
        currentFragment = fragment
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
                R.id.historyMenuItem -> setFragment(historyFragment)
                R.id.mainMenuItem -> setFragment(mainFragment)
                R.id.infoMenuItem -> setFragment(infoFragment)
//                R.id.notificationMenuItem -> setFragment(notificationFragment)
            }; true
        }

        bottomNav.menu.findItem(R.id.mainMenuItem).isChecked = true
    }

    private fun initStartFragment() {
        val preferences = getPreferences(Context.MODE_PRIVATE)
        if (preferences!!.getBoolean(getString(R.string.first_start_key), true)) {
            setFragment(calibrationFragment)
            findViewById<BottomNavigationView>(R.id.bottomNavigationView).isVisible = false
            supportActionBar?.hide()
        }
        else
            setFragment(currentFragment ?: mainFragment)
    }

    private fun initVolumeRecorderOffset() {
        VolumeRecorder.Instance.calibrationOffset = getPreferences(Context.MODE_PRIVATE)
            .getInt(getString(R.string.calibration_key), VolumeRecorder.defaultCalibrationOffset)
    }

    private fun titleByFragment(fragment: Fragment): String {
        return when (fragment) {
            is MainFragment -> getString(R.string.main_controls)
            is CalibrationFragment -> getString(R.string.app_calibration)
            is HistoryFragment -> getString(R.string.volume_live_chart)
            is InfoFragment -> getString(R.string.volume_statistics)
            is NotificationFragment -> getString(R.string.notifications)
            else -> getString(R.string.volume_meter_app)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        startActionBarMenuItem = menu?.findItem(R.id.startRecordingActionBarMenuItem)!!
        pauseActionBarMenuItem = menu.findItem(R.id.pauseRecordingActionBarMenuItem)!!
        stopActionBarMenuItem = menu.findItem(R.id.stopRecordingActionBarMenuItem)!!
        updateActionBarItemVisibilities(VolumeRecorder.Instance.recordingState)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.startRecordingActionBarMenuItem -> { VolumeRecorder.Instance.switchRecording(); true }
            R.id.pauseRecordingActionBarMenuItem -> { VolumeRecorder.Instance.switchRecording(); true }
            R.id.stopRecordingActionBarMenuItem -> { VolumeRecorder.Instance.stopRecording(); true }
            else -> false
        }
    }

    private fun updateActionBarItemVisibilities(recordingState: RecordingState) {
        when (recordingState) {
            RecordingState.STARTED -> {
                startActionBarMenuItem?.isEnabled = false; pauseActionBarMenuItem?.isEnabled = true; stopActionBarMenuItem?.isEnabled = true
                startActionBarMenuItem?.icon?.alpha = 120; pauseActionBarMenuItem?.icon?.alpha = 255; stopActionBarMenuItem?.icon?.alpha = 255
            }
            RecordingState.PAUSED -> {
                startActionBarMenuItem?.isEnabled = true; pauseActionBarMenuItem?.isEnabled = false; stopActionBarMenuItem?.isEnabled = true
                startActionBarMenuItem?.icon?.alpha = 255; pauseActionBarMenuItem?.icon?.alpha = 120; stopActionBarMenuItem?.icon?.alpha = 255
            }
            RecordingState.STOPPED -> {
                startActionBarMenuItem?.isEnabled = true; pauseActionBarMenuItem?.isEnabled = false; stopActionBarMenuItem?.isEnabled = false
                startActionBarMenuItem?.icon?.alpha = 255; pauseActionBarMenuItem?.icon?.alpha = 120; stopActionBarMenuItem?.icon?.alpha = 120
            }
        }
    }
}