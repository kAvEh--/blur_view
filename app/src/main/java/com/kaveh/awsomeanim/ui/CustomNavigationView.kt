package com.kaveh.awsomeanim.ui

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import androidx.core.view.iterator
import com.google.android.material.bottomnavigation.BottomNavigationView

class CustomNavigationView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr) {
    private val mPaint = Paint()
    private lateinit var mPoint: Pair<Float, Float>
    private lateinit var mListener: OnNavigationItemSelectedListener
    private var isListenerBound = false
    private var mIndicatorColor = Color.parseColor("#03DAC5")

    init {
        this.setOnNavigationItemSelectedListener { item ->
            if (::mListener.isInitialized) mListener.onNavigationItemSelected(item)
            moveIndicatorX((2 * findSelectedItem(item.itemId) + 1) * (width / (this.menu.size() * 2F)))
            true
        }
        isListenerBound = true

        mPaint.style = Paint.Style.FILL
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.isAntiAlias = true
        mPaint.color = mIndicatorColor
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mPoint = Pair((2 * findSelectedItem(this.selectedItemId) + 1) * (width / (this.menu.size() * 2F)), height * .7F)
    }

    private fun moveIndicatorX(location: Float) {
        val clickEffectAnim = ObjectAnimator.ofFloat(
                this, "indicatorLocationX", mPoint.first, location)
        clickEffectAnim.duration = 200
        clickEffectAnim.interpolator = AccelerateInterpolator()
        clickEffectAnim.start()
    }

    var indicatorLocationX: Float
        get() = mPoint.first
        set(location) {
            if (mPoint.first != location) {
                mPoint = Pair(location, mPoint.second)
                invalidate()
            }
        }

    private fun findSelectedItem(itemId: Int): Int {
        var index = 0
        for (i in this.menu) {
            if (i.itemId == itemId) {
                return index
            }
            index++
        }
        return 0
    }

    override fun setOnNavigationItemSelectedListener(listener: OnNavigationItemSelectedListener?) {
        if (listener != null) {
            mListener = listener
        }
        if (!isListenerBound)
            super.setOnNavigationItemSelectedListener(listener)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(mPoint.first, mPoint.second, 7F, mPaint)
    }

//    fun transform(fab: FloatingActionButton) {
//        if (fab.isVisible) {
//            fab.hide(object : FloatingActionButton.OnVisibilityChangedListener() {
//                override fun onHidden(fab: FloatingActionButton?) {
//                    super.onHidden(fab)
//                    ValueAnimator.ofFloat(materialShapeDrawable.interpolation, 0F).apply {
//                        addUpdateListener { materialShapeDrawable.interpolation = it.animatedValue as Float }
//                        start()
//                    }
//                }
//            })
//        } else {
//            ValueAnimator.ofFloat(materialShapeDrawable.interpolation, 1F).apply {
//                addUpdateListener { materialShapeDrawable.interpolation = it.animatedValue as Float }
//                doOnEnd { fab.show() }
//                start()
//            }
//        }
//    }
}