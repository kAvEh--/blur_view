package com.kaveh.awsomeanim

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Build
import android.os.Bundle
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.android.material.snackbar.Snackbar
import com.kaveh.awsomeanim.ui.ArcProgressView
import kotlinx.android.synthetic.main.activity_arc_progress.*

class ArcProgressActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arc_progress)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val arc = findViewById<ArcProgressView>(R.id.arc_progress)
        val myLogo = (ResourcesCompat.getDrawable(this.resources, R.drawable.ic_home_black_24dp, null) as VectorDrawable).toBitmap()
//        arc.setIndicatorBitmap(myLogo)
        ((findViewById<SeekBar>(R.id.seek))).setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                arc.progress = i / 100F
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

}
