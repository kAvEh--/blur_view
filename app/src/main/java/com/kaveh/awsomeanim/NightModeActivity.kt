package com.kaveh.awsomeanim

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_night_mode.*


class NightModeActivity : AppCompatActivity() {

    private var flag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_night_mode)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            if (flag) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                flag = false
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                flag = true
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val currentNightMode = newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                println("fuuuuuuuuuuuuuuuuuuuuuck")} // Night mode is not active, we're using the light theme
            Configuration.UI_MODE_NIGHT_YES -> {
                println("kiiiiiiiiiiiiiiiiiiiiiiiiiiiiil")} // Night mode is active, we're using dark theme
        }
    }

}
