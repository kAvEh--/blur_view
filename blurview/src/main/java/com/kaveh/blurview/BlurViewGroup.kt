package com.kaveh.blurview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.View
import android.view.ViewGroup


class BlurViewGroup {
    companion object {
        fun ViewGroup.blur(context: Context, radius: Float) {
            val b = getBitmapFromView(this)

            val mBlurBitmap = blurBitmap(context, b!!, radius)
            val d: Drawable = BitmapDrawable(context.resources, mBlurBitmap)
            val blurredView = View(context)
            blurredView.setTag("kaveh")
            blurredView.background = d
            this.addView(blurredView)
        }

        private fun getBitmapFromView(view: View): Bitmap? {
            val bitmap =
                    Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            return bitmap
        }

        fun ViewGroup.remove() {
            val tmp = this.findViewWithTag<View>("kaveh")
            if (tmp != null) {
                this.removeView(tmp)
            }
        }

        private fun blurBitmap(context: Context, mBitmap: Bitmap, radius: Float): Bitmap? {
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
}