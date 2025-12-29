package com.soosu.admobnative

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import com.google.android.gms.ads.nativead.AdChoicesView
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

/**
 * Enum class representing different native ad template types.
 */
enum class AdTemplateType(val value: Int) {
    SMALL(0),
    MEDIUM(1),
    LARGE(2),
    HEADLINE(3),
    ICON_SMALL(4),
    CONTENT(5),
    FULL_WIDTH_MEDIA(6),
    APP_INSTALL(7);

    companion object {
        fun fromValue(value: Int): AdTemplateType {
            return entries.find { it.value == value } ?: SMALL
        }
    }
}

/**
 * Custom View for displaying native ads with various template styles.
 *
 * Usage in XML:
 * ```xml
 * <com.soosu.admobnative.NativeAdTemplateView
 *     android:id="@+id/native_ad_view"
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content"
 *     app:adTemplate="medium"
 *     app:adBackgroundColor="@color/surface"
 *     app:adTextColor="@color/onSurface" />
 * ```
 *
 * Usage in code:
 * ```kotlin
 * val adView = NativeAdTemplateView(context)
 * adView.setTemplate(AdTemplateType.MEDIUM)
 * adView.setNativeAd(nativeAd)
 * ```
 */
class NativeAdTemplateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var templateType: AdTemplateType = AdTemplateType.SMALL
    private var nativeAdView: NativeAdView? = null
    private var currentNativeAd: NativeAd? = null

    @ColorInt
    private var backgroundColor: Int = Color.parseColor("#F5F5F5")

    @ColorInt
    private var textColor: Int = Color.parseColor("#222222")

    @ColorInt
    private var ctaButtonColor: Int = Color.parseColor("#1976D2")

    @ColorInt
    private var ctaTextColor: Int = Color.WHITE

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.NativeAdTemplateView)
            try {
                val templateValue = typedArray.getInt(R.styleable.NativeAdTemplateView_adTemplate, 0)
                templateType = AdTemplateType.fromValue(templateValue)
                backgroundColor = typedArray.getColor(
                    R.styleable.NativeAdTemplateView_adBackgroundColor,
                    backgroundColor
                )
                textColor = typedArray.getColor(
                    R.styleable.NativeAdTemplateView_adTextColor,
                    textColor
                )
                ctaButtonColor = typedArray.getColor(
                    R.styleable.NativeAdTemplateView_adCtaButtonColor,
                    ctaButtonColor
                )
                ctaTextColor = typedArray.getColor(
                    R.styleable.NativeAdTemplateView_adCtaTextColor,
                    ctaTextColor
                )
            } finally {
                typedArray.recycle()
            }
        }
        inflateTemplate()
    }

    /**
     * Set the template type programmatically.
     * Call this before setNativeAd() if changing template.
     */
    fun setTemplate(type: AdTemplateType) {
        if (templateType != type) {
            templateType = type
            inflateTemplate()
            currentNativeAd?.let { setNativeAd(it) }
        }
    }

    /**
     * Set the background color.
     */
    fun setAdBackgroundColor(@ColorInt color: Int) {
        backgroundColor = color
        currentNativeAd?.let { setNativeAd(it) }
    }

    /**
     * Set the text color.
     */
    fun setAdTextColor(@ColorInt color: Int) {
        textColor = color
        currentNativeAd?.let { setNativeAd(it) }
    }

    /**
     * Set the CTA button color.
     */
    fun setCtaButtonColor(@ColorInt color: Int) {
        ctaButtonColor = color
        currentNativeAd?.let { setNativeAd(it) }
    }

    /**
     * Set the CTA text color.
     */
    fun setCtaTextColor(@ColorInt color: Int) {
        ctaTextColor = color
        currentNativeAd?.let { setNativeAd(it) }
    }

    private fun inflateTemplate() {
        removeAllViews()
        val layoutRes = when (templateType) {
            AdTemplateType.SMALL -> R.layout.gnt_ad_small_template_view
            AdTemplateType.MEDIUM -> R.layout.gnt_ad_medium_template_view
            AdTemplateType.LARGE -> R.layout.gnt_ad_large_template_view
            AdTemplateType.HEADLINE -> R.layout.gnt_ad_headline_template_view
            AdTemplateType.ICON_SMALL -> R.layout.gnt_ad_icon_small_template_view
            AdTemplateType.CONTENT -> R.layout.gnt_ad_content_template_view
            AdTemplateType.FULL_WIDTH_MEDIA -> R.layout.gnt_ad_fullwidth_media_template_view
            AdTemplateType.APP_INSTALL -> R.layout.gnt_ad_app_install_template_view
        }
        val view = LayoutInflater.from(context).inflate(layoutRes, this, false)
        addView(view)
        nativeAdView = view as? NativeAdView
    }

    /**
     * Set the native ad to display.
     */
    fun setNativeAd(nativeAd: NativeAd) {
        currentNativeAd = nativeAd
        val adView = nativeAdView ?: return

        when (templateType) {
            AdTemplateType.SMALL -> bindSmallTemplate(adView, nativeAd)
            AdTemplateType.MEDIUM -> bindMediumTemplate(adView, nativeAd)
            AdTemplateType.LARGE -> bindLargeTemplate(adView, nativeAd)
            AdTemplateType.HEADLINE -> bindHeadlineTemplate(adView, nativeAd)
            AdTemplateType.ICON_SMALL -> bindIconSmallTemplate(adView, nativeAd)
            AdTemplateType.CONTENT -> bindContentTemplate(adView, nativeAd)
            AdTemplateType.FULL_WIDTH_MEDIA -> bindFullWidthMediaTemplate(adView, nativeAd)
            AdTemplateType.APP_INSTALL -> bindAppInstallTemplate(adView, nativeAd)
        }

        adView.setNativeAd(nativeAd)
    }

    /**
     * Get the current NativeAdView for advanced customization.
     */
    fun getNativeAdView(): NativeAdView? = nativeAdView

    private fun bindSmallTemplate(adView: NativeAdView, nativeAd: NativeAd) {
        val background = adView.findViewById<View>(R.id.background)
        val primary = adView.findViewById<TextView>(R.id.primary)
        val secondary = adView.findViewById<TextView>(R.id.secondary)
        val ad = adView.findViewById<TextView>(R.id.ad)
        val icon = adView.findViewById<ImageView>(R.id.icon)
        val iconContainer = adView.findViewById<View>(R.id.icon_container)
        val adImage = adView.findViewById<ImageView>(R.id.ad_image)
        val adImageContainer = adView.findViewById<View>(R.id.ad_image_container)
        val adChoice = adView.findViewById<AdChoicesView>(R.id.adChoice)

        adView.adChoicesView = adChoice
        adView.callToActionView = background
        adView.headlineView = primary
        adView.iconView = icon

        background.setBackgroundColor(backgroundColor)
        primary.setTextColor(textColor)
        secondary.setTextColor(textColor)

        // Set AD badge
        ad.setTextColor(textColor)
        ad.background = GradientDrawable().apply {
            setColor(ColorUtils.setAlphaComponent(textColor, 38))
            cornerRadius = 6f * resources.displayMetrics.density
        }

        // Set secondary text
        secondary.text = when {
            !nativeAd.body.isNullOrEmpty() -> nativeAd.body
            !nativeAd.advertiser.isNullOrEmpty() -> nativeAd.advertiser
            !nativeAd.store.isNullOrEmpty() -> nativeAd.store
            !nativeAd.callToAction.isNullOrEmpty() -> nativeAd.callToAction
            else -> "ˑˑˑ"
        }

        nativeAd.headline?.let { primary.text = it }

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
    }

    private fun bindMediumTemplate(adView: NativeAdView, nativeAd: NativeAd) {
        val background = adView.findViewById<View>(R.id.background)
        val primary = adView.findViewById<TextView>(R.id.primary)
        val secondary = adView.findViewById<TextView>(R.id.secondary)
        val description = adView.findViewById<TextView>(R.id.description)
        val ad = adView.findViewById<TextView>(R.id.ad)
        val cta = adView.findViewById<TextView>(R.id.cta)
        val arrow = adView.findViewById<ImageView>(R.id.arrow)
        val icon = adView.findViewById<ImageView>(R.id.icon)
        val iconContainer = adView.findViewById<View>(R.id.icon_container)
        val adMedia = adView.findViewById<MediaView>(R.id.ad_media)
        val adImage = adView.findViewById<ImageView>(R.id.ad_image)
        val adImageContainer = adView.findViewById<View>(R.id.ad_image_container)
        val adChoice = adView.findViewById<AdChoicesView>(R.id.adChoice)

        adView.adChoicesView = adChoice
        adView.headlineView = primary
        adView.iconView = icon
        adView.bodyView = description
        adView.mediaView = adMedia

        background.setBackgroundColor(backgroundColor)
        primary.setTextColor(textColor)
        secondary.setTextColor(textColor)
        description.setTextColor(textColor)
        cta.setTextColor(textColor)
        arrow.setColorFilter(textColor)

        ad.setTextColor(textColor)
        ad.background = GradientDrawable().apply {
            setColor(ColorUtils.setAlphaComponent(textColor, 38))
            cornerRadius = 6f * resources.displayMetrics.density
        }

        secondary.text = when {
            !nativeAd.body.isNullOrEmpty() -> nativeAd.body
            !nativeAd.advertiser.isNullOrEmpty() -> nativeAd.advertiser
            !nativeAd.store.isNullOrEmpty() -> nativeAd.store
            !nativeAd.callToAction.isNullOrEmpty() -> nativeAd.callToAction
            else -> "ˑˑˑ"
        }

        nativeAd.headline?.let { primary.text = it }
        nativeAd.callToAction?.let { cta.text = it }

        nativeAd.icon?.drawable?.let { drawable ->
            iconContainer.visibility = View.VISIBLE
            icon.visibility = View.VISIBLE
            icon.setImageDrawable(drawable)
        } ?: run {
            iconContainer.visibility = View.GONE
        }

        nativeAd.body?.let { body ->
            description.text = body
            description.visibility = View.VISIBLE
        } ?: run {
            description.visibility = View.GONE
        }

        nativeAd.mediaContent?.let { mediaContent ->
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
            nativeAd.images.firstOrNull()?.let { image ->
                adMedia.visibility = View.GONE
                adImage.visibility = View.VISIBLE
                adImage.setImageDrawable(image.drawable)
                adImageContainer.visibility = View.VISIBLE
            }
        }
    }

    private fun bindLargeTemplate(adView: NativeAdView, nativeAd: NativeAd) {
        val background = adView.findViewById<View>(R.id.background)
        val primary = adView.findViewById<TextView>(R.id.primary)
        val secondary = adView.findViewById<TextView>(R.id.secondary)
        val description = adView.findViewById<TextView>(R.id.description)
        val ad = adView.findViewById<TextView>(R.id.ad)
        val cta = adView.findViewById<TextView>(R.id.cta)
        val ctaContainer = adView.findViewById<View>(R.id.cta_container)
        val icon = adView.findViewById<ImageView>(R.id.icon)
        val iconContainer = adView.findViewById<View>(R.id.icon_container)
        val adMedia = adView.findViewById<MediaView>(R.id.ad_media)
        val adImage = adView.findViewById<ImageView>(R.id.ad_image)
        val adImageContainer = adView.findViewById<View>(R.id.ad_image_container)
        val adChoice = adView.findViewById<AdChoicesView>(R.id.adChoice)
        val ratingBar = adView.findViewById<RatingBar>(R.id.rating_bar)
        val price = adView.findViewById<TextView>(R.id.price)

        adView.adChoicesView = adChoice
        adView.callToActionView = ctaContainer
        adView.headlineView = primary
        adView.iconView = icon
        adView.bodyView = description
        adView.starRatingView = ratingBar
        adView.priceView = price
        adView.mediaView = adMedia

        background.setBackgroundColor(backgroundColor)
        primary.setTextColor(textColor)
        secondary.setTextColor(textColor)
        description.setTextColor(textColor)
        cta.setTextColor(ctaTextColor)
        ctaContainer.backgroundTintList = android.content.res.ColorStateList.valueOf(ctaButtonColor)

        ad.setTextColor(textColor)
        ad.background = GradientDrawable().apply {
            setColor(ColorUtils.setAlphaComponent(textColor, 38))
            cornerRadius = 6f * resources.displayMetrics.density
        }

        secondary.text = when {
            !nativeAd.body.isNullOrEmpty() -> nativeAd.body
            !nativeAd.advertiser.isNullOrEmpty() -> nativeAd.advertiser
            !nativeAd.store.isNullOrEmpty() -> nativeAd.store
            !nativeAd.callToAction.isNullOrEmpty() -> nativeAd.callToAction
            else -> "ˑˑˑ"
        }

        nativeAd.headline?.let { primary.text = it }
        nativeAd.callToAction?.let { cta.text = it }

        nativeAd.icon?.drawable?.let { drawable ->
            iconContainer.visibility = View.VISIBLE
            icon.visibility = View.VISIBLE
            icon.setImageDrawable(drawable)
        } ?: run {
            iconContainer.visibility = View.GONE
        }

        nativeAd.body?.let { body ->
            description.text = body
            description.visibility = View.VISIBLE
        } ?: run {
            description.visibility = View.GONE
        }

        nativeAd.mediaContent?.let { mediaContent ->
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
            nativeAd.images.firstOrNull()?.let { image ->
                adMedia.visibility = View.GONE
                adImage.visibility = View.VISIBLE
                adImage.setImageDrawable(image.drawable)
                adImageContainer.visibility = View.VISIBLE
            }
        }

        nativeAd.starRating?.let { rating ->
            ratingBar.rating = rating.toFloat()
            ratingBar.visibility = View.VISIBLE
        }

        nativeAd.price?.let { priceValue ->
            price.text = priceValue
            price.visibility = View.VISIBLE
        }
    }

    private fun bindHeadlineTemplate(adView: NativeAdView, nativeAd: NativeAd) {
        val background = adView.findViewById<View>(R.id.background)
        val primary = adView.findViewById<TextView>(R.id.primary)
        val cta = adView.findViewById<TextView>(R.id.cta)
        val bar = adView.findViewById<TextView>(R.id.bar)
        val arrow = adView.findViewById<ImageView>(R.id.arrow)
        val adChoice = adView.findViewById<AdChoicesView>(R.id.adChoice)

        adView.adChoicesView = adChoice
        adView.headlineView = primary

        background.setBackgroundColor(backgroundColor)
        primary.setTextColor(textColor)
        cta.setTextColor(textColor)
        bar.setTextColor(textColor)
        arrow.setColorFilter(textColor)

        nativeAd.headline?.let { primary.text = it }
        nativeAd.callToAction?.let { cta.text = it }
    }

    private fun bindIconSmallTemplate(adView: NativeAdView, nativeAd: NativeAd) {
        val background = adView.findViewById<View>(R.id.background)
        val primary = adView.findViewById<TextView>(R.id.primary)
        val secondary = adView.findViewById<TextView>(R.id.secondary)
        val ad = adView.findViewById<TextView>(R.id.ad)
        val icon = adView.findViewById<ImageView>(R.id.icon)
        val iconContainer = adView.findViewById<View>(R.id.icon_container)
        val adChoice = adView.findViewById<AdChoicesView>(R.id.adChoice)

        adView.adChoicesView = adChoice
        adView.headlineView = primary
        adView.iconView = icon

        background.setBackgroundColor(backgroundColor)
        primary.setTextColor(textColor)
        secondary.setTextColor(textColor)

        ad.setTextColor(textColor)
        ad.background = GradientDrawable().apply {
            setColor(ColorUtils.setAlphaComponent(textColor, 38))
            cornerRadius = 6f * resources.displayMetrics.density
        }

        secondary.text = when {
            !nativeAd.body.isNullOrEmpty() -> nativeAd.body
            !nativeAd.advertiser.isNullOrEmpty() -> nativeAd.advertiser
            !nativeAd.store.isNullOrEmpty() -> nativeAd.store
            !nativeAd.callToAction.isNullOrEmpty() -> nativeAd.callToAction
            else -> "ˑˑˑ"
        }

        nativeAd.headline?.let { primary.text = it }

        nativeAd.icon?.drawable?.let { drawable ->
            iconContainer.visibility = View.VISIBLE
            icon.visibility = View.VISIBLE
            icon.setImageDrawable(drawable)
        } ?: run {
            iconContainer.visibility = View.GONE
        }
    }

    private fun bindContentTemplate(adView: NativeAdView, nativeAd: NativeAd) {
        val background = adView.findViewById<View>(R.id.background)
        val headline = adView.findViewById<TextView>(R.id.headline)
        val primary = adView.findViewById<TextView>(R.id.primary)
        val description = adView.findViewById<TextView>(R.id.description)
        val sponsoredLabel = adView.findViewById<TextView>(R.id.sponsored_label)
        val cta = adView.findViewById<TextView>(R.id.cta)
        val ctaContainer = adView.findViewById<View>(R.id.cta_container)
        val icon = adView.findViewById<ImageView>(R.id.icon)
        val iconContainer = adView.findViewById<View>(R.id.icon_container)
        val adMedia = adView.findViewById<MediaView>(R.id.ad_media)
        val adImage = adView.findViewById<ImageView>(R.id.ad_image)
        val adImageContainer = adView.findViewById<View>(R.id.ad_image_container)
        val adChoice = adView.findViewById<AdChoicesView>(R.id.adChoice)

        adView.adChoicesView = adChoice
        adView.callToActionView = ctaContainer
        adView.headlineView = headline
        adView.iconView = icon
        adView.bodyView = description
        adView.mediaView = adMedia

        background.setBackgroundColor(backgroundColor)
        headline.setTextColor(textColor)
        primary.setTextColor(textColor)
        description.setTextColor(ColorUtils.blendARGB(textColor, backgroundColor, 0.3f))
        sponsoredLabel.setTextColor(ColorUtils.blendARGB(textColor, backgroundColor, 0.4f))
        ctaContainer.backgroundTintList = android.content.res.ColorStateList.valueOf(ctaButtonColor)
        cta.setTextColor(ctaTextColor)

        if (!nativeAd.headline.isNullOrEmpty()) {
            headline.text = nativeAd.headline
        } else if (!nativeAd.advertiser.isNullOrEmpty()) {
            headline.text = nativeAd.advertiser
        } else if (!nativeAd.store.isNullOrEmpty()) {
            headline.text = nativeAd.store
        }

        nativeAd.body?.let { primary.text = it }
        nativeAd.callToAction?.let { cta.text = it }

        nativeAd.icon?.drawable?.let { drawable ->
            iconContainer.visibility = View.VISIBLE
            icon.setImageDrawable(drawable)
        } ?: run {
            iconContainer.visibility = View.GONE
        }

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

        nativeAd.mediaContent?.let { mediaContent ->
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
            nativeAd.images.firstOrNull()?.let { image ->
                adMedia.visibility = View.GONE
                adImage.visibility = View.VISIBLE
                adImage.setImageDrawable(image.drawable)
                adImageContainer.visibility = View.VISIBLE
            } ?: run {
                adImageContainer.visibility = View.GONE
            }
        }
    }

    private fun bindFullWidthMediaTemplate(adView: NativeAdView, nativeAd: NativeAd) {
        val primary = adView.findViewById<TextView>(R.id.primary)
        val primaryFallback = adView.findViewById<TextView>(R.id.primary_fallback)
        val secondary = adView.findViewById<TextView>(R.id.secondary)
        val description = adView.findViewById<TextView>(R.id.description)
        val ad = adView.findViewById<TextView>(R.id.ad)
        val cta = adView.findViewById<TextView>(R.id.cta)
        val ctaFallback = adView.findViewById<TextView>(R.id.cta_fallback)
        val ctaContainer = adView.findViewById<View>(R.id.cta_container)
        val icon = adView.findViewById<ImageView>(R.id.icon)
        val iconContainer = adView.findViewById<View>(R.id.icon_container)
        val adMedia = adView.findViewById<MediaView>(R.id.ad_media)
        val adImage = adView.findViewById<ImageView>(R.id.ad_image)
        val adImageContainer = adView.findViewById<View>(R.id.ad_image_container)
        val fallbackContainer = adView.findViewById<View>(R.id.fallback_container)
        val gradientOverlay = adView.findViewById<View>(R.id.gradient_overlay)
        val adChoice = adView.findViewById<AdChoicesView>(R.id.adChoice)

        adView.adChoicesView = adChoice
        adView.callToActionView = ctaContainer
        adView.headlineView = primary
        adView.iconView = icon
        adView.bodyView = description
        adView.mediaView = adMedia

        ctaContainer.backgroundTintList = android.content.res.ColorStateList.valueOf(ctaButtonColor)
        cta.setTextColor(ctaTextColor)

        nativeAd.headline?.let { headline ->
            primary.text = headline
            primaryFallback.text = headline
        }

        ad.setTextColor(ctaButtonColor)
        ad.background = GradientDrawable().apply {
            setColor(ctaTextColor)
            cornerRadius = 6f * resources.displayMetrics.density
        }

        secondary.text = when {
            !nativeAd.body.isNullOrEmpty() -> nativeAd.body
            !nativeAd.advertiser.isNullOrEmpty() -> nativeAd.advertiser
            !nativeAd.store.isNullOrEmpty() -> nativeAd.store
            !nativeAd.callToAction.isNullOrEmpty() -> nativeAd.callToAction
            else -> "ˑˑˑ"
        }

        nativeAd.callToAction?.let { callToAction ->
            cta.text = callToAction
            ctaFallback.text = callToAction
        }

        nativeAd.body?.let { body ->
            description.text = body
            description.visibility = View.VISIBLE
        } ?: run {
            description.visibility = View.GONE
        }

        nativeAd.icon?.drawable?.let { drawable ->
            iconContainer.visibility = View.VISIBLE
            icon.setImageDrawable(drawable)
        } ?: run {
            iconContainer.visibility = View.GONE
        }

        nativeAd.mediaContent?.let { mediaContent ->
            adMedia.setMediaContent(mediaContent)
            adMedia.visibility = View.VISIBLE
            adImageContainer.visibility = View.VISIBLE
            fallbackContainer.visibility = View.GONE
            applyGradientOverlay(gradientOverlay)
        } ?: run {
            nativeAd.images.firstOrNull()?.let { image ->
                adMedia.visibility = View.GONE
                adImage.visibility = View.VISIBLE
                adImage.setImageDrawable(image.drawable)
                adImageContainer.visibility = View.VISIBLE
                fallbackContainer.visibility = View.GONE
                applyGradientOverlay(gradientOverlay)
            } ?: run {
                adImageContainer.visibility = View.GONE
                fallbackContainer.visibility = View.VISIBLE
            }
        }
    }

    private fun bindAppInstallTemplate(adView: NativeAdView, nativeAd: NativeAd) {
        val background = adView.findViewById<View>(R.id.background)
        val primary = adView.findViewById<TextView>(R.id.primary)
        val secondary = adView.findViewById<TextView>(R.id.secondary)
        val description = adView.findViewById<TextView>(R.id.description)
        val ad = adView.findViewById<TextView>(R.id.ad)
        val cta = adView.findViewById<TextView>(R.id.cta)
        val ctaContainer = adView.findViewById<View>(R.id.cta_container)
        val icon = adView.findViewById<ImageView>(R.id.icon)
        val iconContainer = adView.findViewById<View>(R.id.icon_container)
        val adMedia = adView.findViewById<MediaView>(R.id.ad_media)
        val adImage = adView.findViewById<ImageView>(R.id.ad_image)
        val adImageContainer = adView.findViewById<View>(R.id.ad_image_container)
        val adChoice = adView.findViewById<AdChoicesView>(R.id.adChoice)
        val ratingBar = adView.findViewById<RatingBar>(R.id.rating_bar)
        val ratingText = adView.findViewById<TextView>(R.id.rating_text)
        val price = adView.findViewById<TextView>(R.id.price)
        val separator = adView.findViewById<View>(R.id.separator)

        adView.adChoicesView = adChoice
        adView.callToActionView = ctaContainer
        adView.headlineView = primary
        adView.iconView = icon
        adView.bodyView = description
        adView.starRatingView = ratingBar
        adView.priceView = price
        adView.mediaView = adMedia

        background.backgroundTintList = android.content.res.ColorStateList.valueOf(backgroundColor)
        primary.setTextColor(textColor)
        secondary.setTextColor(ColorUtils.blendARGB(textColor, backgroundColor, 0.4f))
        description.setTextColor(ColorUtils.blendARGB(textColor, backgroundColor, 0.3f))
        ratingText.setTextColor(ColorUtils.blendARGB(textColor, backgroundColor, 0.4f))
        ctaContainer.backgroundTintList = android.content.res.ColorStateList.valueOf(ctaButtonColor)
        cta.setTextColor(ctaTextColor)

        ad.setTextColor(textColor)
        ad.background = GradientDrawable().apply {
            setColor(ColorUtils.setAlphaComponent(textColor, 38))
            cornerRadius = 6f * resources.displayMetrics.density
        }

        nativeAd.headline?.let { primary.text = it }

        secondary.text = when {
            !nativeAd.body.isNullOrEmpty() -> nativeAd.body
            !nativeAd.advertiser.isNullOrEmpty() -> nativeAd.advertiser
            !nativeAd.store.isNullOrEmpty() -> nativeAd.store
            !nativeAd.callToAction.isNullOrEmpty() -> nativeAd.callToAction
            else -> "ˑˑˑ"
        }

        nativeAd.callToAction?.let { cta.text = it } ?: run { cta.text = "Install" }

        nativeAd.icon?.drawable?.let { drawable ->
            iconContainer.visibility = View.VISIBLE
            icon.setImageDrawable(drawable)
        } ?: run {
            iconContainer.visibility = View.GONE
        }

        nativeAd.starRating?.let { rating ->
            ratingBar.rating = rating.toFloat()
            ratingBar.visibility = View.VISIBLE
            ratingText.text = String.format("%.1f", rating)
            ratingText.visibility = View.VISIBLE
        } ?: run {
            ratingBar.visibility = View.GONE
            ratingText.visibility = View.GONE
        }

        nativeAd.price?.let { priceValue ->
            price.text = priceValue
            price.visibility = View.VISIBLE
            separator.visibility = if (ratingBar.visibility == View.VISIBLE) View.VISIBLE else View.GONE
        } ?: run {
            price.text = "Free"
            price.visibility = View.VISIBLE
            separator.visibility = if (ratingBar.visibility == View.VISIBLE) View.VISIBLE else View.GONE
        }

        nativeAd.body?.let { body ->
            description.text = body
            description.visibility = View.VISIBLE
        } ?: run {
            description.visibility = View.GONE
        }

        nativeAd.mediaContent?.let { mediaContent ->
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
            nativeAd.images.firstOrNull()?.let { image ->
                adMedia.visibility = View.GONE
                adImage.visibility = View.VISIBLE
                adImage.setImageDrawable(image.drawable)
                adImageContainer.visibility = View.VISIBLE
            } ?: run {
                adImageContainer.visibility = View.GONE
            }
        }
    }

    private fun applyGradientOverlay(view: View) {
        val shapeDrawable = PaintDrawable()
        shapeDrawable.shape = RectShape()
        shapeDrawable.shaderFactory = object : ShapeDrawable.ShaderFactory() {
            override fun resize(width: Int, height: Int): Shader {
                return LinearGradient(
                    0f, height.toFloat(), 0f, height * 0.3f,
                    intArrayOf(
                        Color.parseColor("#CC000000"),
                        Color.parseColor("#80000000"),
                        Color.TRANSPARENT
                    ),
                    floatArrayOf(0f, 0.5f, 1f),
                    Shader.TileMode.CLAMP
                )
            }
        }
        view.background = shapeDrawable
    }
}
