package com.soosu.admobnative

import android.annotation.SuppressLint
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
import com.soosu.admobnative.databinding.GntAdContentTemplateViewBinding

/**
 * Content feed-style ad template that blends naturally with social media feeds.
 *
 * Features:
 * - Social media post-style layout
 * - Profile icon + advertiser name header
 * - "Sponsored" label for transparency
 * - Post-style headline with natural line spacing
 * - Full-width media with rounded corners
 * - Engagement-style CTA button
 *
 * Best used for:
 * - News feed placements
 * - Content discovery feeds
 * - Social media style apps
 * - Blog/article listings
 *
 * CTR Optimization:
 * - Native feel reduces ad blindness
 * - Familiar social post layout increases engagement
 * - Clear but non-intrusive sponsorship disclosure
 *
 * @param nativeAd The native ad to display
 * @param modifier Compose modifier
 * @param backgroundColor Background color
 * @param textColor Primary text color
 * @param ctaButtonColor CTA button background color
 * @param ctaTextColor CTA button text color
 */
@SuppressLint("SetTextI18n")
@Composable
fun NativeAdContentBox(
    nativeAd: NativeAd?,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    ctaButtonColor: Color = Color(0xFF1976D2),
    ctaTextColor: Color = Color.White
) {

    Box(modifier = modifier) {

        if (nativeAd != null) {
            val bgColor = backgroundColor.toArgb()
            val txtColor = textColor.toArgb()
            val ctaBgColor = ctaButtonColor.toArgb()
            val ctaTxtColor = ctaTextColor.toArgb()

            AndroidViewBinding(
                factory = GntAdContentTemplateViewBinding::inflate,
            ) {

                val adView = nativeAdView.also { adView ->
                    adView.adChoicesView = adChoice
                    adView.callToActionView = ctaContainer
                    adView.headlineView = headline
                    adView.iconView = icon
                    adView.bodyView = description
                    adView.mediaView = adMedia
                }

                // Set background color
                background.setBackgroundColor(bgColor)

                // Set text colors
                headline.setTextColor(txtColor)
                primary.setTextColor(txtColor)
                description.setTextColor(ColorUtils.blendARGB(txtColor, bgColor, 0.3f))
                sponsoredLabel.setTextColor(ColorUtils.blendARGB(txtColor, bgColor, 0.4f))

                // Configure CTA button
                ctaContainer.setCardBackgroundColor(ctaBgColor)
                cta.setTextColor(ctaTxtColor)
                ctaArrow.setColorFilter(ctaTxtColor)

                // Set advertiser name
                if (!nativeAd.headline.isNullOrEmpty()) {
                    headline.text = nativeAd.headline
                } else if (!nativeAd.advertiser.isNullOrEmpty()) {
                    headline.text = nativeAd.advertiser
                } else if (!nativeAd.store.isNullOrEmpty()) {
                    headline.text = nativeAd.store
                }

                // Use body as fallback for headline
                nativeAd.body?.let { body ->
                    primary.text = body
                }

                // Set call to action
                nativeAd.callToAction?.let { callToAction ->
                    cta.text = callToAction
                }

                // Set icon
                nativeAd.icon?.drawable?.let { drawable ->
                    iconContainer.visibility = View.VISIBLE
                    icon.setImageDrawable(drawable)
                } ?: run {
                    iconContainer.visibility = View.GONE
                }

                // Set body description (additional context below media)
                nativeAd.body?.let { body ->
                    if (nativeAd.headline != null && body != nativeAd.headline) {
                        description.text = body
                        description.visibility = View.VISIBLE
                    } else {
                        description.visibility = View.GONE
                    }
                } ?: run {
                    description.visibility = View.GONE
                }

                // Set media content
                nativeAd.mediaContent?.let { mediaContent ->
                    adMedia.setMediaContent(mediaContent)
                    adMedia.visibility = View.VISIBLE
                    adImageContainer.visibility = View.VISIBLE
                } ?: run {
                    // Fallback to static image
                    nativeAd.images.firstOrNull()?.let { image ->
                        adMedia.visibility = View.GONE
                        adImage.visibility = View.VISIBLE
                        adImage.setImageDrawable(image.drawable)
                        adImageContainer.visibility = View.VISIBLE
                    } ?: run {
                        adImageContainer.visibility = View.GONE
                    }
                }

                adView.setNativeAd(nativeAd)
            }

        }
    }

}

/**
 * Calculate AD badge background color based on the main background color
 */
private fun getAdBadgeBackgroundColor(backgroundColor: Int): Int {
    val luminance = ColorUtils.calculateLuminance(backgroundColor)

    return if (luminance > 0.5) {
        ColorUtils.blendARGB(backgroundColor, android.graphics.Color.BLACK, 0.12f)
    } else {
        ColorUtils.blendARGB(backgroundColor, android.graphics.Color.WHITE, 0.12f)
    }
}

/**
 * Calculate text color for AD badge based on badge background color
 */
private fun getAdBadgeTextColor(badgeBackgroundColor: Int): Int {
    val luminance = ColorUtils.calculateLuminance(badgeBackgroundColor)
    return if (luminance > 0.5) {
        android.graphics.Color.parseColor("#333333")
    } else {
        android.graphics.Color.parseColor("#EEEEEE")
    }
}
