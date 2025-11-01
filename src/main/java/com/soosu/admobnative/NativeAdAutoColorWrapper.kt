package com.soosu.admobnative

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.gms.ads.nativead.NativeAd

/**
 * Wrapper component that automatically extracts colors from native ad images
 * and provides them to the content composable.
 *
 * @param nativeAd The native ad object containing the image
 * @param modifier Modifier for the wrapper
 * @param content Composable lambda that receives extracted backgroundColor and textColor.
 *                Colors will be null if extraction fails or no image is available.
 *
 * Example usage:
 * ```
 * NativeAdAutoColorWrapper(nativeAd = nativeAd) { bgColor, txtColor ->
 *     NativeAdSmallBox(
 *         nativeAd = nativeAd,
 *         backgroundColor = bgColor ?: MaterialTheme.colorScheme.surfaceVariant,
 *         textColor = txtColor ?: MaterialTheme.colorScheme.onBackground
 *     )
 * }
 * ```
 */
@Composable
fun NativeAdAutoColorWrapper(
    nativeAd: NativeAd?,
    modifier: Modifier = Modifier,
    content: @Composable (backgroundColor: Color?, textColor: Color?) -> Unit
) {
    var backgroundColor by remember { mutableStateOf<Color?>(null) }
    var textColor by remember { mutableStateOf<Color?>(null) }

    LaunchedEffect(nativeAd) {
        if (nativeAd != null) {
            val imageDrawable = nativeAd.images.firstOrNull()?.drawable
            val (bgColor, txtColor) = AdColorExtractor.extractColors(imageDrawable)
            backgroundColor = bgColor
            textColor = txtColor
        } else {
            backgroundColor = null
            textColor = null
        }
    }

    content(backgroundColor, textColor)
}
