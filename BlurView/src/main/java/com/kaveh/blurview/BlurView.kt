package com.kaveh.blurview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.AttributeSet
import androidx.core.graphics.drawable.toBitmap


class BlurView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {

    private var originalDrawable: Drawable? = null
    private var radius = 20F

    fun blur() {
        if (this.drawable == null)
            return

        if (originalDrawable == null) {
            originalDrawable = this.drawable
        }

        this.setImageBitmap(originalDrawable?.let {
            BitmapDrawable(context.resources, blurBitmap(this, it, radius)).bitmap
        })
    }

    fun delete() {
        this.setImageDrawable(originalDrawable?.let { originalDrawable })
    }

    var blurRadius: Float
        get() = radius
        set(value) {
            if (value > 0 && value <= 25)
                radius = value
        }

    private fun blurBitmap(view: BlurView, drawable: Drawable, radius: Float): Bitmap? {
        val mBitmap = drawable.toBitmap()
        val output = Bitmap.createBitmap(mBitmap.width, mBitmap.height, mBitmap.config)
        val rs = RenderScript.create(context)
        val script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        val inAlloc = Allocation.createFromBitmap(rs, mBitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_GRAPHICS_TEXTURE)
        val outAlloc = Allocation.createFromBitmap(rs, output)
        script.setRadius(radius)
        script.setInput(inAlloc)
        script.forEach(outAlloc)
        outAlloc.copyTo(output)
        rs.destroy()
        val scaled = Bitmap.createScaledBitmap(output, view.measuredWidth, view.measuredHeight, true)
        output.recycle()
        return scaled
    }
}