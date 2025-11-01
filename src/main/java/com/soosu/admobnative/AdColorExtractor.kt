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
     * Priority: MutedSwatch > LightMutedSwatch > DarkMutedSwatch > DominantColor
     * Prioritizes muted colors for better readability
     */
    private fun extractBackgroundColor(palette: Palette): Color? {
        val swatch = palette.mutedSwatch
            ?: palette.lightMutedSwatch
            ?: palette.darkMutedSwatch
            ?: palette.dominantSwatch

        val color = swatch?.rgb?.let { Color(it) } ?: return null

        // Adjust color brightness for optimal readability
        return adjustColorForReadability(color)
    }

    /**
     * Adjusts color brightness for optimal readability
     * Ensures the color is neither too dark nor too bright
     * @param color The original color
     * @return Adjusted color with better readability
     */
    private fun adjustColorForReadability(color: Color): Color {
        val luminance = calculateLuminance(color)

        // If color is too dark (luminance < 0.15), brighten it
        // If color is too bright (luminance > 0.85), darken it
        // Target range: 0.15 ~ 0.85 for comfortable reading
        return when {
            luminance < 0.15f -> {
                // Brighten the color
                adjustBrightness(color, 0.3f)
            }
            luminance > 0.85f -> {
                // Darken the color
                adjustBrightness(color, 0.7f)
            }
            else -> color
        }
    }

    /**
     * Adjusts the brightness of a color
     * @param color Original color
     * @param factor Brightness factor (0.0 = black, 1.0 = original brightness, >1.0 = brighter)
     * @return Color with adjusted brightness
     */
    private fun adjustBrightness(color: Color, factor: Float): Color {
        val r = (color.red * factor).coerceIn(0f, 1f)
        val g = (color.green * factor).coerceIn(0f, 1f)
        val b = (color.blue * factor).coerceIn(0f, 1f)
        return Color(r, g, b, color.alpha)
    }

    /**
     * Calculates appropriate text color based on background brightness
     * Uses WCAG 2.0 guidelines for better contrast ratio (minimum 4.5:1)
     * @param backgroundColor The background color
     * @return Black or White color for optimal contrast
     */
    private fun getContrastTextColor(backgroundColor: Color): Color {
        val bgLuminance = calculateLuminance(backgroundColor)

        // Calculate contrast ratios for both black and white text
        val whiteLuminance = 1f
        val blackLuminance = 0f

        val contrastWithWhite = calculateContrastRatio(bgLuminance, whiteLuminance)
        val contrastWithBlack = calculateContrastRatio(bgLuminance, blackLuminance)

        // Choose the color with better contrast ratio
        // For optimal readability, prefer the one with higher contrast
        return if (contrastWithWhite > contrastWithBlack) {
            Color.White
        } else {
            Color.Black
        }
    }

    /**
     * Calculates the contrast ratio between two luminance values
     * Based on WCAG 2.0 formula
     * @return Contrast ratio (1:1 to 21:1)
     */
    private fun calculateContrastRatio(luminance1: Float, luminance2: Float): Float {
        val lighter = maxOf(luminance1, luminance2)
        val darker = minOf(luminance1, luminance2)
        return (lighter + 0.05f) / (darker + 0.05f)
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
