package com.soosu.admobnative.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.soosu.admobnative.NativeAdTemplateView

class ViewSampleActivity : AppCompatActivity() {

    private val nativeAds = mutableListOf<NativeAd>()
    private val testAdUnitId = "ca-app-pub-3940256099942544/2247696110"

    private lateinit var adSmall: NativeAdTemplateView
    private lateinit var adIconSmall: NativeAdTemplateView
    private lateinit var adHeadline: NativeAdTemplateView
    private lateinit var adMedium: NativeAdTemplateView
    private lateinit var adLarge: NativeAdTemplateView
    private lateinit var adContent: NativeAdTemplateView
    private lateinit var adFullWidth: NativeAdTemplateView
    private lateinit var adAppInstall: NativeAdTemplateView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_sample)

        setupToolbar()
        initViews()
        loadAllAds()
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun initViews() {
        adSmall = findViewById(R.id.ad_small)
        adIconSmall = findViewById(R.id.ad_icon_small)
        adHeadline = findViewById(R.id.ad_headline)
        adMedium = findViewById(R.id.ad_medium)
        adLarge = findViewById(R.id.ad_large)
        adContent = findViewById(R.id.ad_content)
        adFullWidth = findViewById(R.id.ad_full_width)
        adAppInstall = findViewById(R.id.ad_app_install)
    }

    private fun loadAllAds() {
        loadAdFor(adSmall)
        loadAdFor(adIconSmall)
        loadAdFor(adHeadline)
        loadAdFor(adMedium)
        loadAdFor(adLarge)
        loadAdFor(adContent)
        loadAdFor(adFullWidth)
        loadAdFor(adAppInstall)
    }

    private fun loadAdFor(templateView: NativeAdTemplateView) {
        val adLoader = AdLoader.Builder(this, testAdUnitId)
            .forNativeAd { ad ->
                nativeAds.add(ad)
                templateView.setNativeAd(ad)
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    // Handle error silently
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    .setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_RIGHT)
                    .build()
            )
            .build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    override fun onDestroy() {
        super.onDestroy()
        nativeAds.forEach { it.destroy() }
        nativeAds.clear()
    }
}
