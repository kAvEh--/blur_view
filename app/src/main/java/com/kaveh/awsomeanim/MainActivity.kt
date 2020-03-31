package com.kaveh.awsomeanim

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var navButton: Button
    private lateinit var waveButton: Button
    private lateinit var arcButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navButton = findViewById(R.id.nav_button)
        navButton.setOnClickListener {
            val intent = Intent(this, NavigationActivity::class.java)
            startActivity(intent)
        }

        waveButton = findViewById(R.id.wave_button)
        waveButton.setOnClickListener {
            val intent = Intent(this, WaveActivity::class.java)
            startActivity(intent)
        }
        arcButton = findViewById(R.id.arc_progress_button)
        arcButton.setOnClickListener {
            val intent = Intent(this, ArcProgressActivity::class.java)
            startActivity(intent)
        }
    }
}