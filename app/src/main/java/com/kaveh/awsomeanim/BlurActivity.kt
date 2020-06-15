package com.kaveh.awsomeanim

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.kaveh.blurview.BlurView

class BlurActivity : AppCompatActivity() {

    var flag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blur)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<BlurView>(R.id.imageView).blurRadius = 3F
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            if (flag) {
                findViewById<BlurView>(R.id.imageView).delete()
                flag = false
            } else {
                findViewById<BlurView>(R.id.imageView).blur()
                flag = true
            }
        }
    }
}