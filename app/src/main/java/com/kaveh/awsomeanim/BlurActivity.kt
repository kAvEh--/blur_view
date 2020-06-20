package com.kaveh.awsomeanim

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kaveh.blurview.BlurView
import com.kaveh.blurview.BlurViewGroup
import com.kaveh.blurview.BlurViewGroup.Companion.blur

class BlurActivity : AppCompatActivity() {

    private var flag1 = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blur)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<BlurView>(R.id.imageView).blurRadius = 3F
        findViewById<Button>(R.id.button2).setOnClickListener {
            flag1 = if (flag1) {
                with(BlurViewGroup.Companion) {
                    val viewGroup = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
                    viewGroup.remove()
                }
                false
            } else {
                with(BlurViewGroup.Companion) {
                    val viewGroup = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
                    viewGroup.blur(this@BlurActivity, 20F)
                }
                true
            }
        }
        findViewById<SeekBar>(R.id.seekBar).setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                findViewById<BlurView>(R.id.imageView).blurRadius = progress / 4F
                findViewById<BlurView>(R.id.imageView).blur()
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
            }
        })
    }
}