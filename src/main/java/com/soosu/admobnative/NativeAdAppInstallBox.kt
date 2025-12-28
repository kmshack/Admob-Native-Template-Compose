package com.soosu.admobnative

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.util.Log
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
import com.soosu.admobnative.databinding.GntAdAppInstallTemplateViewBinding

/**
 * App install ad template optimized for app promotion and downloads.
 *
 * Features:
 * - App Store style layout
 * - Large app icon with rounded corners
 * - Star rating display
 * - Price/Free indicator
 * - Prominent "Install" button
 * - Optional screenshot/media preview
 *
 * Best used for:
 * - App promotion campaigns
 * - Game advertisements
 * - App store style placements
 * - Mobile app discovery feeds
 *
 * CTR Optimization:
 * - Familiar app store layout increases trust
 * - Clear rating display builds credibility
 * - Prominent install button drives action
 * - Price transparency reduces friction
 *
 * @param nativeAd The native ad to display
 * @param modifier Compose modifier
 * @param backgroundColor Card background color
 * @param textColor Primary text color
 * @param ctaButtonColor Install button background color
 * @param ctaTextColor Install button text color
 */
@SuppressLint("SetTextI18n")
@Composable
fun NativeAdAppInstallBox(
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
                factory = GntAdAppInstallTemplateViewBinding::inflate,
            ) {

                val adView = nativeAdView.also { adView ->
                    adView.adChoicesView = adChoice
                    adView.callToActionView = ctaContainer
                    adView.headlineView = primary
                    adView.iconView = icon
                    adView.bodyView = description
                    adView.starRatingView = ratingBar
                    adView.priceView = price
                    adView.mediaView = adMedia
                }

                // Set background color
                background.backgroundTintList = android.content.res.ColorStateList.valueOf(bgColor)

                // Set text colors
                primary.setTextColor(txtColor)
                secondary.setTextColor(ColorUtils.blendARGB(txtColor, bgColor, 0.4f))
                description.setTextColor(ColorUtils.blendARGB(txtColor, bgColor, 0.3f))
                ratingText.setTextColor(ColorUtils.blendARGB(txtColor, bgColor, 0.4f))

                // Configure CTA button
                ctaContainer.backgroundTintList = android.content.res.ColorStateList.valueOf(ctaBgColor)
                cta.setTextColor(ctaTxtColor)

                // Set AD badge colors (harmonize with other text)
                ad.setTextColor(txtColor)
                ad.background = GradientDrawable().apply {
                    setColor(ColorUtils.setAlphaComponent(txtColor, 38))
                    cornerRadius = 6f * ad.context.resources.displayMetrics.density
                }

                // Set headline (app name)
                nativeAd.headline?.let { headline ->
                    primary.text = headline
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
                } ?: run {
                    cta.text = "Install"
                }

                // Set icon
                nativeAd.icon?.drawable?.let { drawable ->
                    iconContainer.visibility = View.VISIBLE
                    icon.setImageDrawable(drawable)
                } ?: run {
                    iconContainer.visibility = View.GONE
                }

                // Set star rating
                nativeAd.starRating?.let { rating ->
                    ratingBar.rating = rating.toFloat()
                    ratingBar.visibility = View.VISIBLE
                    ratingText.text = String.format("%.1f", rating)
                    ratingText.visibility = View.VISIBLE
                } ?: run {
                    ratingBar.visibility = View.GONE
                    ratingText.visibility = View.GONE
                }

                // Set price
                nativeAd.price?.let { priceValue ->
                    price.text = priceValue
                    price.visibility = View.VISIBLE
                    separator.visibility = if (ratingBar.visibility == View.VISIBLE) View.VISIBLE else View.GONE
                } ?: run {
                    // Show "Free" if no price specified
                    price.text = "Free"
                    price.visibility = View.VISIBLE
                    separator.visibility = if (ratingBar.visibility == View.VISIBLE) View.VISIBLE else View.GONE
                }

                // Set body description
                nativeAd.body?.let { body ->
                    description.text = body
                    description.visibility = View.VISIBLE
                } ?: run {
                    description.visibility = View.GONE
                }

                // Set media content (screenshots)
                nativeAd.mediaContent?.let { mediaContent ->
                    Log.d(
                        "NativeAdAppInstallBox",
                        "MediaContent - aspectRatio: ${mediaContent.aspectRatio}"
                    )
                    adMedia.setMediaContent(mediaContent)
                    adMedia.post {
                        val width = adMedia.width
                        val height = (width / mediaContent.aspectRatio).toInt()
                        adMedia.layoutParams = adMedia.layoutParams.apply {
                            this.height = height
                        }
                    }
                    adMedia.visibility = View.VISIBLE
                    adImageContainer.visibility = View.VISIBLE
                } ?: run {
                    // Fallback to static image
                    nativeAd.images.firstOrNull()?.let { image ->
                        val width = image.drawable?.intrinsicWidth ?: 0
                        val height = image.drawable?.intrinsicHeight ?: 0
                        val aspectRatio = if (height > 0) width.toFloat() / height.toFloat() else 0f
                        Log.d(
                            "NativeAdAppInstallBox",
                            "Image - width: $width, height: $height, aspectRatio: $aspectRatio"
                        )
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
