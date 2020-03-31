package com.kaveh.awsomeanim

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import com.kaveh.awsomeanim.ui.WaveView
import com.larswerkman.holocolorpicker.ColorPicker
import com.larswerkman.holocolorpicker.OpacityBar
import kotlinx.android.synthetic.main.activity_navigation.*


class WaveActivity : AppCompatActivity() {
    private var mBorderColor: Int = Color.parseColor("#000000")
    private var mBorderWidth = 5
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wave)
        val waveView1 = findViewById<WaveView>(R.id.wave1)
        val waveView2 = findViewById<WaveView>(R.id.wave2)
        val waveView3 = findViewById<WaveView>(R.id.wave3)
        waveView1.setBorder(mBorderWidth.toFloat(), mBorderColor)
        waveView2.setBorder(mBorderWidth.toFloat(), mBorderColor)
        waveView3.setBorder(mBorderWidth.toFloat(), mBorderColor)
        waveView1.startAnimation(1000)
        waveView2.startAnimation(500)
        waveView3.startAnimation(800)
//        mWaveHelper = WaveHelper(waveView)
//        (findViewById<SeekBar>(R.id.seekBar))
//                .setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
//                    override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
//                        mBorderWidth = i
//                        waveView.setBorder(mBorderWidth.toFloat(), mBorderColor)
//                    }
//
//                    override fun onStartTrackingTouch(seekBar: SeekBar) {}
//                    override fun onStopTrackingTouch(seekBar: SeekBar) {}
//                })
//        CompoundButtonCompat.setButtonTintList(
//                (findViewById<View>(R.id.colorDefault) as RadioButton),
//                resources.getColorStateList(R.color.white, null))
//        CompoundButtonCompat.setButtonTintList(
//                (findViewById<View>(R.id.colorRed) as RadioButton),
//                resources.getColorStateList(R.color.red, null))
//        CompoundButtonCompat.setButtonTintList(
//                (findViewById<View>(R.id.colorGreen) as RadioButton),
//                resources.getColorStateList(R.color.green, null))
//        CompoundButtonCompat.setButtonTintList(
//                (findViewById<View>(R.id.colorBlue) as RadioButton),
//                resources.getColorStateList(R.color.blue, null))\
        (findViewById<SeekBar>(R.id.level)).setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
               waveView1.waterLevelRatio = i / 100F
               waveView2.waterLevelRatio = i / 100F
               waveView3.waterLevelRatio = i / 100F
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        val pickerBack = findViewById<ColorPicker>(R.id.picker_back)
        val pickerFront = findViewById<ColorPicker>(R.id.picker_front)
        val opacityBar = findViewById<OpacityBar>(R.id.opacitybar)
        pickerBack.setOnColorSelectedListener {
            waveView1.setWaveColor(
                    ColorUtils.setAlphaComponent(pickerBack.color, opacityBar.opacity),
                    ColorUtils.setAlphaComponent(pickerFront.color, opacityBar.opacity))
            waveView2.setWaveColor(
                    ColorUtils.setAlphaComponent(pickerBack.color, opacityBar.opacity),
                    ColorUtils.setAlphaComponent(pickerFront.color, opacityBar.opacity))
            waveView3.setWaveColor(
                    ColorUtils.setAlphaComponent(pickerBack.color, opacityBar.opacity),
                    ColorUtils.setAlphaComponent(pickerFront.color, opacityBar.opacity))
        }
        pickerFront.setOnColorSelectedListener {
            waveView1.setWaveColor(
                    ColorUtils.setAlphaComponent(pickerBack.color, opacityBar.opacity),
                    ColorUtils.setAlphaComponent(pickerFront.color, opacityBar.opacity))
            waveView2.setWaveColor(
                    ColorUtils.setAlphaComponent(pickerBack.color, opacityBar.opacity),
                    ColorUtils.setAlphaComponent(pickerFront.color, opacityBar.opacity))
            waveView3.setWaveColor(
                    ColorUtils.setAlphaComponent(pickerBack.color, opacityBar.opacity),
                    ColorUtils.setAlphaComponent(pickerFront.color, opacityBar.opacity))
        }
        opacityBar.setOnOpacityChangedListener {
            waveView1.setWaveColor(
                    ColorUtils.setAlphaComponent(pickerBack.color, opacityBar.opacity),
                    ColorUtils.setAlphaComponent(pickerFront.color, opacityBar.opacity))
            waveView2.setWaveColor(
                    ColorUtils.setAlphaComponent(pickerBack.color, opacityBar.opacity),
                    ColorUtils.setAlphaComponent(pickerFront.color, opacityBar.opacity))
            waveView3.setWaveColor(
                    ColorUtils.setAlphaComponent(pickerBack.color, opacityBar.opacity),
                    ColorUtils.setAlphaComponent(pickerFront.color, opacityBar.opacity))
        }
    }

    override fun onPause() {
        super.onPause()
//        mWaveHelper!!.cancel()
    }

    override fun onResume() {
        super.onResume()
//        mWaveHelper!!.start()
    }
}