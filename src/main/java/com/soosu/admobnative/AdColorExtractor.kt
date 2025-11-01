package com.soosu.admobnative

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.sqrt

/**
 * Extracts dominant colors from a drawable image using Palette library
 */
object AdColorExtractor {

    /**
     * Extracts background and text colors from a drawable
     * @param drawable The source drawable to extract colors from
     * @return Pair of (backgroundColor, textColor) or (null, null) if extraction fails
     */
    suspend fun extractColors(drawable: Drawable?): Pair<Color?, Color?> {
        if (drawable == null) return null to null

        return withContext(Dispatchers.Default) {
            var bitmapToRecycle: Bitmap? = null
            try {
                val (bitmap, shouldRecycle) = drawableToBitmap(drawable)
                if (shouldRecycle) {
                    bitmapToRecycle = bitmap
                }

                val palette = Palette.from(bitmap).generate()

                val backgroundColor = extractBackgroundColor(palette)
                val textColor = backgroundColor?.let { getContrastTextColor(it) }

                backgroundColor to textColor
            } catch (e: Exception) {
                null to null
            } finally {
                bitmapToRecycle?.recycle()
            }
        }
    }

    /**
     * Converts a Drawable to Bitmap
     * @return Pair of (bitmap, shouldRecycle) - shouldRecycle is true if we created a new bitmap
     */
    private fun drawableToBitmap(drawable: Drawable): Pair<Bitmap, Boolean> {
        // For BitmapDrawable, use the bitmap directly but don't recycle it
        if (drawable is BitmapDrawable && drawable.bitmap != null) {
            return drawable.bitmap to false
        }

        // For other drawables, create a new bitmap
        val bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        } else {
            Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
        }

        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap to true
    }

    /**
     * Extracts the most appropriate background color from palette
     * Priority: VibrantSwatch > LightVibrantSwatch > MutedSwatch > DominantColor
     */
    private fun extractBackgroundColor(palette: Palette): Color? {
        val swatch = palette.vibrantSwatch
            ?: palette.lightVibrantSwatch
            ?: palette.mutedSwatch
            ?: palette.dominantSwatch

        return swatch?.rgb?.let { Color(it) }
    }

    /**
     * Calculates appropriate text color based on background brightness
     * @param backgroundColor The background color
     * @return Black or White color for optimal contrast
     */
    private fun getContrastTextColor(backgroundColor: Color): Color {
        val luminance = calculateLuminance(backgroundColor)
        return if (luminance > 0.5f) Color.Black else Color.White
    }

    /**
     * Calculates the relative luminance of a color
     * Based on WCAG 2.0 formula
     */
    private fun calculateLuminance(color: Color): Float {
        val r = color.red
        val g = color.green
        val b = color.blue

        // Convert to linear RGB
        val rLinear = if (r <= 0.03928f) r / 12.92f else ((r + 0.055f) / 1.055f).pow(2.4f)
        val gLinear = if (g <= 0.03928f) g / 12.92f else ((g + 0.055f) / 1.055f).pow(2.4f)
        val bLinear = if (b <= 0.03928f) b / 12.92f else ((b + 0.055f) / 1.055f).pow(2.4f)

        // Calculate luminance
        return 0.2126f * rLinear + 0.7152f * gLinear + 0.0722f * bLinear
    }

    private fun Float.pow(exponent: Float): Float {
        return Math.pow(this.toDouble(), exponent.toDouble()).toFloat()
    }
}
