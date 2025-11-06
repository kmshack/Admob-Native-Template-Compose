package com.soosu.admobnative

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.google.android.gms.ads.nativead.NativeAd
import com.soosu.admobnative.databinding.GntAdHeadlineTemplateViewBinding

@SuppressLint("SetTextI18n")
@Composable
fun NativeAdHeadlineBox(
    nativeAd: NativeAd?,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
    textColor: Color = MaterialTheme.colorScheme.onBackground
) {

    Box(modifier = modifier, contentAlignment = Alignment.Center) {

        if (nativeAd != null) {
            val bgColor = backgroundColor.toArgb()
            val txtColor = textColor.toArgb()

            AndroidViewBinding(
                factory = GntAdHeadlineTemplateViewBinding::inflate,
            ) {

                val adView = nativeAdView.also { adView ->
                    adView.adChoicesView = adChoice
                    adView.callToActionView = background
                    adView.headlineView = primary
                }

                background.setBackgroundColor(bgColor)
                primary.setTextColor(txtColor)
                cta.setTextColor(txtColor)
                bar.setTextColor(txtColor)
                arrow.setColorFilter(textColor.toArgb())


                nativeAd.headline?.let { headline ->
                    primary.text = headline
                }

                nativeAd.callToAction?.let { callToAction ->
                    cta.text = callToAction
                }

                adView.setNativeAd(nativeAd)
            }

        }
    }

}
