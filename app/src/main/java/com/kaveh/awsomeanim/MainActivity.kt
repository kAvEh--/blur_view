package com.kaveh.awsomeanim

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val taskDescription = TaskDescription("sdfasfd", R.drawable.ic_notifications_black_24dp, resources.getColor(R.color.colorAccent))
//        (this as Activity).setTaskDescription(taskDescription)

//        val darkButton = findViewById<Button>(R.id.dark_button)
//        darkButton.setOnClickListener {
//            val intent = Intent(this, NightModeActivity::class.java)
//            startActivity(intent)
//        }

        val navButton = findViewById<Button>(R.id.nav_button_1)
        navButton.setOnClickListener {
            val intent = Intent(this, NavigationActivity::class.java)
            intent.putExtra("type", "trail")
            startActivity(intent)
        }

        val navButton2 = findViewById<Button>(R.id.nav_button_2)
        navButton2.setOnClickListener {
            val intent = Intent(this, NavigationActivity::class.java)
            intent.putExtra("type", "point")
            startActivity(intent)
        }

        val navButton3 = findViewById<Button>(R.id.nav_button_3)
        navButton3.setOnClickListener {
            val intent = Intent(this, NavigationActivity::class.java)
            intent.putExtra("type", "fall")
            startActivity(intent)
        }

        val navButton4 = findViewById<Button>(R.id.nav_button_4)
        navButton4.setOnClickListener {
            val intent = Intent(this, NavigationActivity::class.java)
            intent.putExtra("type", "moveup")
            startActivity(intent)
        }

        val waveButton = findViewById<Button>(R.id.wave_button)
        waveButton.setOnClickListener {
            val intent = Intent(this, WaveActivity::class.java)
            startActivity(intent)
        }
        val arcButton = findViewById<Button>(R.id.arc_progress_button)
        arcButton.setOnClickListener {
            val intent = Intent(this, ArcProgressActivity::class.java)
            startActivity(intent)
        }
        val blurButton = findViewById<Button>(R.id.blur_button)
        blurButton.setOnClickListener {
            val intent = Intent(this, BlurActivity::class.java)
            startActivity(intent)
        }
    }
}