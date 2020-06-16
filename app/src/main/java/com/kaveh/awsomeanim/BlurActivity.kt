package com.kaveh.awsomeanim

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kaveh.blurview.BlurView
import com.kaveh.blurview.BlurViewGroup
import com.kaveh.blurview.BlurViewGroup.Companion.blur

class BlurActivity : AppCompatActivity() {

    var flag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blur)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<BlurView>(R.id.imageView).blurRadius = 3F
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            flag = if (flag) {
//                findViewById<BlurView>(R.id.imageView).delete()
                with(BlurViewGroup.Companion) {
                    val viewGroup = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
                    viewGroup.remove()
                }
                false
            } else {
//                findViewById<BlurView>(R.id.imageView).blur()
                with(BlurViewGroup.Companion) {
                    val viewGroup = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
                    viewGroup.blur(this@BlurActivity, 20F)
                }
                true
            }
        }
    }
}