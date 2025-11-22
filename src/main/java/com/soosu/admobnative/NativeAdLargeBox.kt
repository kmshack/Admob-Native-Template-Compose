package com.soosu.admobnative

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
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
import com.soosu.admobnative.databinding.GntAdLargeTemplateViewBinding

@SuppressLint("SetTextI18n")
@Composable
fun NativeAdLargeBox(
    nativeAd: NativeAd?,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
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
                factory = GntAdLargeTemplateViewBinding::inflate,
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

                background.setBackgroundColor(bgColor)
                secondary.setTextColor(txtColor)
                primary.setTextColor(txtColor)
                description.setTextColor(txtColor)
                cta.setTextColor(ctaTxtColor)
                ctaContainer.setCardBackgroundColor(ctaBgColor)
                ctaArrow.setColorFilter(ctaTxtColor)

                // Set AD badge background and text color
                val adBadgeBgColor = getAdBadgeBackgroundColor(bgColor)
                val adBadgeTextColor = getAdBadgeTextColor(adBadgeBgColor)
                ad.setTextColor(adBadgeTextColor)
                ad.background = GradientDrawable().apply {
                    setColor(adBadgeBgColor)
                    cornerRadius = 6f * ad.context.resources.displayMetrics.density
                }

                // Set advertiser or store
                if (!nativeAd.advertiser.isNullOrEmpty()) {
                    secondary.text = " ⋅ ${nativeAd.advertiser}"
                } else if (!nativeAd.store.isNullOrEmpty()) {
                    secondary.text = " ⋅ ${nativeAd.store}"
                } else {
                    secondary.text = " ⋅⋅⋅"
                }

                // Set headline
                nativeAd.headline?.let { headline ->
                    primary.text = headline
                }

                // Set call to action
                nativeAd.callToAction?.let { callToAction ->
                    cta.text = callToAction
                }

                // Set icon
                nativeAd.icon?.drawable?.let { drawable ->
                    iconContainer.visibility = View.VISIBLE
                    icon.visibility = View.VISIBLE
                    icon.setImageDrawable(drawable)
                } ?: run {
                    iconContainer.visibility = View.GONE
                }

                // Set body description
                nativeAd.body?.let { body ->
                    description.text = body
                    description.visibility = View.VISIBLE
                } ?: run {
                    description.visibility = View.GONE
                }

                // Set media content (video or image)
                nativeAd.mediaContent?.let { mediaContent ->
                    adMedia.setMediaContent(mediaContent)
                    adMedia.visibility = View.VISIBLE
                    adImageContainer.visibility = View.VISIBLE
                } ?: run {
                    // Fallback to static image if no media content
                    nativeAd.images.firstOrNull()?.let { image ->
                        adMedia.visibility = View.GONE
                        adImage.visibility = View.VISIBLE
                        adImage.setImageDrawable(image.drawable)
                        adImageContainer.visibility = View.VISIBLE
                    }
                }

                // Set star rating
                nativeAd.starRating?.let { rating ->
                    ratingBar.rating = rating.toFloat()
                    ratingBar.visibility = View.VISIBLE
                }

                // Set price
                nativeAd.price?.let { priceValue ->
                    price.text = priceValue
                    price.visibility = View.VISIBLE
                }

                adView.setNativeAd(nativeAd)
            }

        }
    }

}

/**
 * Calculate AD badge background color based on the main background color
 * Makes it slightly darker or lighter for contrast
 */
private fun getAdBadgeBackgroundColor(backgroundColor: Int): Int {
    val luminance = ColorUtils.calculateLuminance(backgroundColor)

    return if (luminance > 0.5) {
        // For light backgrounds, make the badge darker
        ColorUtils.blendARGB(backgroundColor, android.graphics.Color.BLACK, 0.15f)
    } else {
        // For dark backgrounds, make the badge lighter
        ColorUtils.blendARGB(backgroundColor, android.graphics.Color.WHITE, 0.15f)
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
