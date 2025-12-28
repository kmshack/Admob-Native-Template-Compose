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
//                    adView.callToActionView = background
                    adView.headlineView = primary
                    adView.iconView = icon
                }

                background.setBackgroundColor(bgColor)
                secondary.setTextColor(txtColor)
                primary.setTextColor(txtColor)

                // Set AD badge colors (harmonize with other text)
                ad.setTextColor(txtColor)
                ad.background = GradientDrawable().apply {
                    setColor(ColorUtils.setAlphaComponent(txtColor, 38))
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

                nativeAd.headline?.let { headline ->
                    primary.text = headline
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
