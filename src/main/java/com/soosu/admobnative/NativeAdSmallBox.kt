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
import com.soosu.admobnative.databinding.GntAdSmallTemplateViewBinding

@SuppressLint("SetTextI18n")
@Composable
fun NativeAdSmallBox(
    nativeAd: NativeAd?,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    textColor: Color = MaterialTheme.colorScheme.onBackground
) {

    Box(modifier = modifier) {

        if (nativeAd != null) {
            val bgColor = backgroundColor.toArgb()
            val txtColor = textColor.toArgb()

            AndroidViewBinding(
                factory = GntAdSmallTemplateViewBinding::inflate,
            ) {

                val adView = nativeAdView.also { adView ->
                    adView.adChoicesView = adChoice
                    adView.callToActionView = background
                    adView.headlineView = primary
                    adView.iconView = icon
                }

                background.setBackgroundColor(bgColor)
                secondary.setTextColor(txtColor)
                primary.setTextColor(txtColor)
                cta.setTextColor(txtColor)
                arrow.setColorFilter(txtColor)

                // Set AD badge background and text color
                val adBadgeBgColor = getAdBadgeBackgroundColor(bgColor)
                val adBadgeTextColor = getAdBadgeTextColor(adBadgeBgColor)
                ad.setTextColor(adBadgeTextColor)
                ad.background = GradientDrawable().apply {
                    setColor(adBadgeBgColor)
                    cornerRadius = 6f * ad.context.resources.displayMetrics.density
                }


                if (!nativeAd.advertiser.isNullOrEmpty()) {
                    secondary.text = " ⋅ ${nativeAd.advertiser}"
                } else if (!nativeAd.store.isNullOrEmpty()) {
                    secondary.text = " ⋅ ${nativeAd.store}"
                } else {
                    secondary.text = " ⋅⋅⋅"
                }


                nativeAd.headline?.let { headline ->
                    primary.text = headline
                }

                nativeAd.callToAction?.let { callToAction ->
                    cta.text = callToAction
                }

                nativeAd.icon?.drawable?.let { drawable ->
                    iconContainer.visibility = View.VISIBLE
                    icon.visibility = View.VISIBLE
                    icon.setImageDrawable(drawable)
                } ?: run {
                    iconContainer.visibility = View.GONE
                }

                nativeAd.images.firstOrNull()?.let { image ->
                    adImage.setImageDrawable(image.drawable)
                    adImageContainer.visibility = View.VISIBLE
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
