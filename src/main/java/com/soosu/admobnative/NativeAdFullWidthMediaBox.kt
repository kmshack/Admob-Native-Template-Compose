package com.soosu.admobnative

import android.annotation.SuppressLint
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.graphics.ColorUtils
import com.google.android.gms.ads.nativead.NativeAd
import com.soosu.admobnative.databinding.GntAdFullwidthMediaTemplateViewBinding

/**
 * Full-width media-centric ad template optimized for high visual impact and CTR.
 *
 * Features:
 * - Large media/image display (280dp height)
 * - Gradient overlay for text readability
 * - Overlay CTA button for immediate action
 * - Fallback layout when no media is available
 *
 * Best used for:
 * - Hero placements
 * - Full-screen interstitial-style native ads
 * - High-impact banner replacements
 *
 * @param nativeAd The native ad to display
 * @param modifier Compose modifier
 * @param ctaButtonColor CTA button background color
 * @param ctaTextColor CTA button text color
 */
@SuppressLint("SetTextI18n")
@Composable
fun NativeAdFullWidthMediaBox(
    nativeAd: NativeAd?,
    modifier: Modifier = Modifier,
    ctaButtonColor: Color = Color.White,
    ctaTextColor: Color = Color(0xFF1976D2)
) {

    Box(modifier = modifier) {

        if (nativeAd != null) {
            val ctaBgColor = ctaButtonColor.toArgb()
            val ctaTxtColor = ctaTextColor.toArgb()

            AndroidViewBinding(
                factory = GntAdFullwidthMediaTemplateViewBinding::inflate,
            ) {

                val adView = nativeAdView.also { adView ->
                    adView.adChoicesView = adChoice
                    adView.callToActionView = ctaContainer
                    adView.headlineView = primary
                    adView.iconView = icon
                    adView.bodyView = description
                    adView.mediaView = adMedia
                }

                // Configure CTA button colors
                ctaContainer.setCardBackgroundColor(ctaBgColor)
                cta.setTextColor(ctaTxtColor)

                // Set headline
                nativeAd.headline?.let { headline ->
                    primary.text = headline
                    primaryFallback.text = headline
                }

                // Set AD badge with semi-transparent background for overlay style
                ad.setTextColor(ctaBgColor)
                ad.background = GradientDrawable().apply {
                    setColor(ctaTxtColor)
                    cornerRadius = 6f * ad.context.resources.displayMetrics.density
                }

                // Set secondary text with fallback chain: body -> advertiser -> store -> callToAction
                secondary.text = when {
                    !nativeAd.body.isNullOrEmpty() -> nativeAd.body
                    !nativeAd.advertiser.isNullOrEmpty() -> nativeAd.advertiser
                    !nativeAd.store.isNullOrEmpty() -> nativeAd.store
                    !nativeAd.callToAction.isNullOrEmpty() -> nativeAd.callToAction
                    else -> "ˑˑˑ"
                }


                // Set call to action
                nativeAd.callToAction?.let { callToAction ->
                    cta.text = callToAction
                    ctaFallback.text = callToAction
                }

                // Set body description (for fallback)
                nativeAd.body?.let { body ->
                    description.text = body
                    description.visibility = View.VISIBLE
                } ?: run {
                    description.visibility = View.GONE
                }

                // Set icon (for fallback)
                nativeAd.icon?.drawable?.let { drawable ->
                    iconContainer.visibility = View.VISIBLE
                    icon.setImageDrawable(drawable)
                } ?: run {
                    iconContainer.visibility = View.GONE
                }

                // Set media content with gradient overlay
                nativeAd.mediaContent?.let { mediaContent ->
                    adMedia.setMediaContent(mediaContent)
                    adMedia.visibility = View.VISIBLE
                    adImageContainer.visibility = View.VISIBLE
                    fallbackContainer.visibility = View.GONE

                    // Apply gradient overlay
                    applyGradientOverlay(gradientOverlay)
                } ?: run {
                    // Fallback to static image if no media content
                    nativeAd.images.firstOrNull()?.let { image ->
                        adMedia.visibility = View.GONE
                        adImage.visibility = View.VISIBLE
                        adImage.setImageDrawable(image.drawable)
                        adImageContainer.visibility = View.VISIBLE
                        fallbackContainer.visibility = View.GONE

                        // Apply gradient overlay
                        applyGradientOverlay(gradientOverlay)
                    } ?: run {
                        // No media at all, show fallback layout
                        adImageContainer.visibility = View.GONE
                        fallbackContainer.visibility = View.VISIBLE
                    }
                }

                adView.setNativeAd(nativeAd)
            }

        }
    }

}

/**
 * Apply a bottom-to-top gradient overlay for text readability
 */
private fun applyGradientOverlay(view: View) {
    val shapeDrawable = PaintDrawable()
    shapeDrawable.shape = RectShape()
    shapeDrawable.shaderFactory = object : ShapeDrawable.ShaderFactory() {
        override fun resize(width: Int, height: Int): Shader {
            return LinearGradient(
                0f, height.toFloat(), 0f, height * 0.3f,
                intArrayOf(
                    android.graphics.Color.parseColor("#CC000000"),
                    android.graphics.Color.parseColor("#80000000"),
                    android.graphics.Color.TRANSPARENT
                ),
                floatArrayOf(0f, 0.5f, 1f),
                Shader.TileMode.CLAMP
            )
        }
    }
    view.background = shapeDrawable
}
