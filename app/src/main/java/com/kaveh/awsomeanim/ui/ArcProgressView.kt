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
    private val mPaintBg = Paint()
    private val mPaintProgress = Paint()
    private val mPaintIndicator = Paint()
    private var strokeWidth = 40F
    private var mPath = Path()
    private var mProgress = 0F
    private lateinit var p0: Pair<Float, Float>
    private lateinit var p1: Pair<Float, Float>
    private lateinit var p2: Pair<Float, Float>
    private var p3: Pair<Float, Float> = Pair(width.toFloat(), height.toFloat() - height / 4f)
    private var isTouched = false
    private val indicatorRadius = 30F
    private lateinit var mBitmap: Bitmap

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        p0 = Pair(indicatorRadius * 2, height.toFloat() - height / 4f)
        p1 = Pair(width / 4f, height / 2f - height / 4f)
        p2 = Pair(width / 2f + width / 4f, height / 2f - height / 4f)
        p3 = Pair(width.toFloat() - indicatorRadius * 2, height.toFloat() - height / 4f)
        createObjects()
    }

    /**
     * set progress to `progress`.
     *
     * @param progress Should be 0 ~ 1. Default to be 0.
     */
    var progress: Float
        get() = mProgress
        set(progress) {
            if (mProgress != progress && (progress >= 0 || progress <= 1)) {
                mProgress = progress
                invalidate()
            }
        }

    fun setIndicatorBitmap(bitmap: Bitmap) {
        mBitmap = bitmap
    }

    private fun createObjects() {
        oval.left = strokeWidth
        oval.top = strokeWidth
        oval.right = width.toFloat() - strokeWidth
        oval.bottom = height * 2F - strokeWidth
        mPaintBg.style = Paint.Style.STROKE
        mPaintBg.strokeWidth = 15.0F
        mPaintBg.strokeCap = Paint.Cap.ROUND
        mPaintBg.isAntiAlias = true
        mPaintBg.color = Color.parseColor("#c1c1c1")
        mPaintBg.setShadowLayer(10.0f, 0.0f, -8.0f, R.color.colorPrimaryDark)
        mPaintProgress.style = Paint.Style.STROKE
        mPaintProgress.strokeWidth = 15.0F
        mPaintProgress.strokeCap = Paint.Cap.ROUND
        mPaintProgress.isAntiAlias = true
        mPaintProgress.color = Color.parseColor("#FF14C5")
        mPaintIndicator.style = Paint.Style.FILL
        mPaintIndicator.strokeCap = Paint.Cap.ROUND
        mPaintIndicator.isAntiAlias = true
        mPaintIndicator.color = Color.parseColor("#FF14C5")
        mPaintIndicator.setShadowLayer(indicatorRadius + 5F, 0.0f, 0.0f, R.color.black)
    }

    override fun onDraw(canvas: Canvas) {
        mPath.moveTo(p0.first, p0.second)
        mPath.cubicTo(p1.first, p1.second,
                p2.first, p2.second,
                p3.first, p3.second
        )
        canvas.drawPath(mPath, mPaintBg)
        val tmp = getPoint(mProgress)
        val path2 = getSubPath(mPath, 0F, mProgress)
        canvas.drawPath(path2, mPaintProgress)

        if (::mBitmap.isInitialized)
            canvas.drawBitmap(mBitmap, tmp.first - mBitmap.width / 2, tmp.second - mBitmap.height / 2, mPaintIndicator)
        else
            canvas.drawCircle(tmp.first, tmp.second, indicatorRadius, mPaintIndicator)
    }

    private fun getSubPath(path: Path, start: Float, end: Float): Path {
        val subPath = Path()
        val pathMeasure = PathMeasure(path, false)
        pathMeasure.getSegment(start * pathMeasure.length, end * pathMeasure.length, subPath, true)
        return subPath
    }

    private fun getPoint(progress: Float): Pair<Float, Float> {
        val mPoint: Pair<Float, Float>
        var x = (1 - progress).toDouble().pow(3.0) * p0.first +
                3 * (1 - progress).toDouble().pow(2.0) * progress * p1.first +
                3 * (1 - progress) * progress.toDouble().pow(2.0) * p2.first +
                progress.toDouble().pow(3.0) * p3.first
        var y = (1 - progress).toDouble().pow(3.0) * p0.second +
                3 * (1 - progress).toDouble().pow(2.0) * progress * p1.second +
                3 * (1 - progress) * progress.toDouble().pow(2.0) * p2.second +
                progress.toDouble().pow(3.0) * p3.second
        if (x < p0.first) {
            x = p0.first.toDouble()
        } else if (x > p3.first) {
            x = p3.first.toDouble()
        }
        if (y > p0.second) {
            y = p0.second.toDouble()
        }
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
                    progress = event.x / width
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