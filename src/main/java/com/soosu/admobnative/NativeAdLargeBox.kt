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
                }

                background.setBackgroundColor(bgColor)
                secondary.setTextColor(txtColor)
                primary.setTextColor(txtColor)
                description.setTextColor(txtColor)
                ad.setTextColor(txtColor)
                cta.setTextColor(ctaTxtColor)
                ctaContainer.setCardBackgroundColor(ctaBgColor)
                ctaArrow.setColorFilter(txtColor)

                // Set advertiser or store
                if (!nativeAd.advertiser.isNullOrEmpty()) {
                    secondary.text = "Sponsored by ${nativeAd.advertiser}"
                } else if (!nativeAd.store.isNullOrEmpty()) {
                    secondary.text = "Available at ${nativeAd.store}"
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
                    icon.visibility = View.VISIBLE
                    icon.setImageDrawable(drawable)
                }

                // Set body description
                nativeAd.body?.let { body ->
                    description.text = body
                    description.visibility = View.VISIBLE
                }

                // Set media image
                nativeAd.images.firstOrNull()?.let { image ->
                    adImage.setImageDrawable(image.drawable)
                    adImageContainer.visibility = View.VISIBLE
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
