package com.kaveh.awsomeanim

import android.graphics.drawable.VectorDrawable
import android.os.Build
import android.os.Bundle
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
        arc.setOnChangeListener(object : ArcProgressView.OnCustomEventListener {
            override fun onChanged(progress: Float) {
            }
        })
        val myLogo = (ResourcesCompat.getDrawable(this.resources, R.drawable.ic_home_black_24dp, null) as VectorDrawable).toBitmap()
//        arc.setIndicatorBitmap(myLogo)
    }

}
