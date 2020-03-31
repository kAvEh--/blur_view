package com.kaveh.awsomeanim.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import com.kaveh.awsomeanim.R
import kotlinx.android.synthetic.main.content_arc_progress.view.*
import kotlin.math.pow
import kotlin.math.sqrt


class ArcProgressView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var oval = RectF()
    private val mPaint = Paint()
    private var strokeWidth = 40F
    private var mPath = Path()
    private var mProgress = .25F
    private lateinit var p0: Pair<Float, Float>
    private lateinit var p1: Pair<Float, Float>
    private lateinit var p2: Pair<Float, Float>
    private var p3: Pair<Float, Float> = Pair(width.toFloat(), height.toFloat() - height / 4f)
    private var isTouched = false
    private val indicatorRadius = 30F
    private lateinit var mBitmap: Bitmap

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        p0 = Pair(0F, height.toFloat() - height / 4f)
        p1 = Pair(width / 4f, height / 2f - height / 4f)
        p2 = Pair(width / 2f + width / 4f, height / 2f - height / 4f)
        p3 = Pair(width.toFloat(), height.toFloat() - height / 4f)
        createObjects()
    }

    fun updateProgress(progress: Float) {
        if (progress < 0 || progress > 1) {
            return
        }
        mProgress = progress
        invalidate()
    }

    fun setIndicatorBitmap(bitmap: Bitmap) {
        mBitmap = bitmap
    }

    private fun createObjects() {
        oval.left = strokeWidth
        oval.top = strokeWidth
        oval.right = width.toFloat() - strokeWidth
        oval.bottom = height * 2F - strokeWidth
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 15.0F
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.isAntiAlias = true
        mPaint.color = Color.parseColor("#131415")
        mPaint.setShadowLayer(10.0f, 0.0f, -8.0f, R.color.colorPrimaryDark)
    }

    override fun onDraw(canvas: Canvas) {
        mPath.moveTo(p0.first, p0.second)
        mPath.cubicTo(p1.first, p1.second,
                p2.first, p2.second,
                p3.first, p3.second
        )
        mPaint.color = Color.parseColor("#c1c1c1")
        canvas.drawPath(mPath, mPaint)
        val tmp = getPoint(mProgress)
        val path2 = getSubPath(mPath, 0F, mProgress)
        mPaint.color = Color.parseColor("#FF14C5")
        canvas.drawPath(path2, mPaint)

        if (::mBitmap.isInitialized)
            canvas.drawBitmap(mBitmap, tmp.first - mBitmap.width / 2, tmp.second - mBitmap.height / 2, mPaint)
        else
            canvas.drawCircle(tmp.first, tmp.second, indicatorRadius, mPaint)
    }

    private fun getSubPath(path: Path, start: Float, end: Float): Path {
        val subPath = Path()
        val pathMeasure = PathMeasure(path, false)
        pathMeasure.getSegment(start * pathMeasure.length, end * pathMeasure.length, subPath, true)
        return subPath
    }

    private fun getPoint(progress: Float): Pair<Float, Float> {
        val mPoint: Pair<Float, Float>
        val x = (1 - progress).toDouble().pow(3.0) * p0.first +
                3 * (1 - progress).toDouble().pow(2.0) * progress * p1.first +
                3 * (1 - progress) * progress.toDouble().pow(2.0) * p2.first +
                progress.toDouble().pow(3.0) * p3.first
        val y = (1 - progress).toDouble().pow(3.0) * p0.second +
                3 * (1 - progress).toDouble().pow(2.0) * progress * p1.second +
                3 * (1 - progress) * progress.toDouble().pow(2.0) * p2.second +
                progress.toDouble().pow(3.0) * p3.second
        mPoint = Pair(x.toFloat(), y.toFloat())
        return mPoint
    }

    private fun isTouchedNear(x: Float, y: Float) {
        val tmp = getPoint(mProgress)
        if (sqrt(((tmp.first - x).pow(2) + (tmp.second - y).pow(2)).toDouble()) < indicatorRadius + 5) {
            isTouched = true
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isTouchedNear(event.x, event.y)
                return true
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {
                //TODO must improve tracking of indicator
                if (isTouched) {
                    updateProgress(event.x / width)
                }
            }
            MotionEvent.ACTION_UP -> {
                isTouched = false
            }
            MotionEvent.ACTION_POINTER_UP -> {
            }
            else -> {
            }
        }
        return super.onTouchEvent(event)
    }
}