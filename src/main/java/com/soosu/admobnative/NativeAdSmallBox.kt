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
import com.google.android.gms.ads.nativead.NativeAd
import com.soosu.admobnative.databinding.GntAdMediumTemplateViewBinding
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
                ad.setTextColor(txtColor)


                if (!nativeAd.advertiser.isNullOrEmpty()) {
                    secondary.text = " ⋅ ${nativeAd.advertiser}"
                } else if (!nativeAd.store.isNullOrEmpty()) {
                    secondary.text = " ⋅ ${nativeAd.store}"
                }


                nativeAd.headline?.let { headline ->
                    this@AndroidViewBinding.primary.text = headline
                }

                nativeAd.icon?.drawable?.let { drawable ->
                    icon.visibility = View.VISIBLE
                    icon.setImageDrawable(drawable)
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
