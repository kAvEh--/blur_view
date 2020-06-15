package com.kaveh.blurview

import android.content.Context
import android.graphics.Bitmap
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

    private var originalBitmap: Drawable? = null
    private var radius = 1F

    fun blur() {
        if (this.drawable == null)
            return

        if (originalBitmap == null) {
            originalBitmap = this.drawable
        }

        this.setImageBitmap(originalBitmap?.let { blurBitmap(it, radius) })
    }

    fun delete() {
        this.setImageDrawable(originalBitmap?.let { originalBitmap })
    }

    var blurRadius: Float
        get() = radius
        set(value) {
            if (value > 0 && value < 100)
                radius = value
        }

    private fun blurBitmap(drawable: Drawable, radius: Float): Bitmap? {
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
        return output
    }


}