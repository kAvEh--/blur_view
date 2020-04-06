package com.kaveh.awsomeanim.ui

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MenuItem
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import androidx.core.view.iterator
import com.google.android.material.bottomnavigation.BottomNavigationView


class CustomNavigationView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr) {
    enum class AnimationType {
        Point, Trail, Fall, MoveUp
    }

    private val mPaint = Paint()
    private val mLinePaint = Paint()
    private lateinit var mPoint: Pair<Float, Float>
    private lateinit var mOldPoint: Pair<Float, Float>
    private lateinit var mP0: Pair<Float, Float>
    private lateinit var mP1: Pair<Float, Float>
    private lateinit var mP2: Pair<Float, Float>
    private lateinit var mP3: Pair<Float, Float>
    private lateinit var mP4: Pair<Float, Float>
    private lateinit var mP5: Pair<Float, Float>
    private lateinit var mP6: Pair<Float, Float>
    private lateinit var mListener: OnNavigationItemSelectedListener
    private var isListenerBound = false
    private var isLineAnimationRun = false
    private var mIndicatorRadius = 6F
    private var mIndicatorColor = Color.parseColor("#03DAC5")
    private var mAnimationTye: AnimationType = AnimationType.Point
    private var mIndicatorHeight = 0F
    private var mPath = Path()

    init {
        this.setOnNavigationItemSelectedListener { item ->
            if (::mListener.isInitialized) mListener.onNavigationItemSelected(item)
            when (mAnimationTye) {
                AnimationType.Point -> moveIndicatorX((2 * findSelectedItem(item.itemId) + 1) * (width / (this.menu.size() * 2F)))
                AnimationType.Trail -> moveIndicatorXType2((2 * findSelectedItem(item.itemId) + 1) * (width / (this.menu.size() * 2F)))
                AnimationType.Fall -> moveIndicatorXType3((2 * findSelectedItem(item.itemId) + 1) * (width / (this.menu.size() * 2F)))
                AnimationType.MoveUp -> moveIndicatorXType4(item, (2 * findSelectedItem(item.itemId) + 1) * (width / (this.menu.size() * 2F)))
            }
            true
        }
        isListenerBound = true

        mPaint.style = Paint.Style.FILL
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.isAntiAlias = true
        mPaint.color = mIndicatorColor
        mLinePaint.style = Paint.Style.FILL
        mLinePaint.strokeCap = Paint.Cap.ROUND
        mLinePaint.isAntiAlias = true
        mLinePaint.strokeWidth = mIndicatorRadius
        mLinePaint.color = mIndicatorColor
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mIndicatorHeight = height * .68F
        mPoint = Pair((2 * findSelectedItem(this.selectedItemId) + 1) * (width / (this.menu.size() * 2F)), mIndicatorHeight)
        mOldPoint = mPoint
        if (mAnimationTye == AnimationType.MoveUp) {
            mP0 = Pair(2 * findSelectedItem(this.selectedItemId) * (width / (this.menu.size() * 2F)), height * .3F)
            mP1 = Pair(mP0.first + 75F, mP0.second)
            mP2 = Pair(mP0.first + 75F, 0F)
            mP3 = Pair(mPoint.first, 0F)
            mP4 = Pair(mPoint.first + 25F, 0F)
            mP5 = Pair(mPoint.first + 25F, 0F)
            mP6 = Pair((2 * findSelectedItem(this.selectedItemId) + 2) * (width / (this.menu.size() * 2F)), mP0.second)

            mPath.reset()
            mPath.moveTo(mP0.first, mP0.second);

            mPath.cubicTo(mP1.first, mP1.second,
                    mP2.first, mP2.second,
                    mP3.first, mP3.second)

            mPath.cubicTo(mP4.first, mP4.second,
                    mP5.first, mP5.second,
                    mP6.first, mP6.second)

            mPath.close()
        }
    }

    private fun moveIndicatorX(location: Float) {
        val clickEffectAnim = ObjectAnimator.ofFloat(
                this, "indicatorLocationX", mPoint.first, location)
        clickEffectAnim.duration = 200
        clickEffectAnim.interpolator = AccelerateInterpolator()
        clickEffectAnim.start()
    }

    private fun moveIndicatorXType2(location: Float) {
        val clickEffectAnim = ObjectAnimator.ofFloat(
                this, "indicatorLocationX", mPoint.first, location)
        clickEffectAnim.duration = 150
        clickEffectAnim.interpolator = AccelerateInterpolator()
        clickEffectAnim.start()
        val clickEffectAnim2 = ObjectAnimator.ofFloat(
                this, "indicatorOldLocationX", mOldPoint.first, location)
        clickEffectAnim2.duration = 150
        clickEffectAnim2.startDelay = 100
        clickEffectAnim2.interpolator = AccelerateInterpolator()
        isLineAnimationRun = true
        clickEffectAnim2.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                isLineAnimationRun = false
                mOldPoint = mPoint
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
        clickEffectAnim2.start()
    }

    private fun moveIndicatorXType3(location: Float) {
        val animatorSet = AnimatorSet()
        animatorSet.start()
        val clickEffectAnim1 = ObjectAnimator.ofFloat(
                this, "indicatorLocationY", mIndicatorHeight, height + 10F)
        clickEffectAnim1.duration = 150
        val clickEffectAnim2 = ObjectAnimator.ofFloat(
                this, "indicatorLocationX", mPoint.first, location)
        clickEffectAnim2.duration = 0
        val clickEffectAnim3 = ObjectAnimator.ofFloat(
                this, "indicatorLocationY", height + 10F, mIndicatorHeight)
        clickEffectAnim3.duration = 500
        clickEffectAnim3.interpolator = BounceInterpolator()
        animatorSet.playSequentially(clickEffectAnim1, clickEffectAnim2, clickEffectAnim3)
        animatorSet.start()
    }

    private fun moveIndicatorXType4(item: MenuItem, location: Float) {
        val animatorSet = AnimatorSet()
        animatorSet.start()
        val clickEffectAnim1 = ObjectAnimator.ofFloat(
                this, "indicatorLocationY", mIndicatorHeight, height + 10F)
        clickEffectAnim1.duration = 150
        val clickEffectAnim2 = ObjectAnimator.ofFloat(
                this, "indicatorLocationX", mPoint.first, location)
        clickEffectAnim2.duration = 0
        val clickEffectAnim3 = ObjectAnimator.ofFloat(
                this, "indicatorLocationY", height + 10F, mIndicatorHeight)
        clickEffectAnim3.duration = 500
        clickEffectAnim3.interpolator = BounceInterpolator()
        animatorSet.playSequentially(clickEffectAnim1, clickEffectAnim2, clickEffectAnim3)
        animatorSet.start()
    }

    private fun moveIndicatorType5(location: Float) {
//        mPath.moveTo(0F, height * .5F)
//        mPath.cubicTo()
    }

    var animationType: AnimationType
        get() = mAnimationTye
        set(type) {
            mAnimationTye = type
        }

    private var indicatorLocationX: Float
        get() = mPoint.first
        set(location) {
            if (mPoint.first != location) {
                mPoint = Pair(location, mPoint.second)
                invalidate()
            }
        }

    private var indicatorOldLocationX: Float
        get() = mOldPoint.first
        set(location) {
            if (mOldPoint.first != location) {
                mOldPoint = Pair(location, mOldPoint.second)
                invalidate()
            }
        }

    private var indicatorLocationY: Float
        get() = mPoint.second
        set(location) {
            if (mPoint.second != location) {
                mPoint = Pair(mPoint.first, location)
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
        if (isLineAnimationRun) {
            canvas.drawLine(mOldPoint.first, mOldPoint.second, mPoint.first, mPoint.second, mLinePaint)
        } else {
            canvas.drawCircle(mPoint.first, mPoint.second, mIndicatorRadius, mPaint)
        }
        if (mAnimationTye == AnimationType.MoveUp) {
            canvas.drawPath(mPath, mPaint)
        }
    }
}