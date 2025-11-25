package com.soosu.admobnative.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.soosu.admobnative.NativeAdAppInstallBox
import com.soosu.admobnative.NativeAdAutoColorWrapper
import com.soosu.admobnative.NativeAdContentBox
import com.soosu.admobnative.NativeAdFullWidthMediaBox
import com.soosu.admobnative.NativeAdHeadlineBox
import com.soosu.admobnative.NativeAdIconSmallBox
import com.soosu.admobnative.NativeAdLargeBox
import com.soosu.admobnative.NativeAdMediumBox
import com.soosu.admobnative.NativeAdSmallBox

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Mobile Ads SDK
        MobileAds.initialize(this) {}

        setContent {
            AdMobNativeSampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun AdMobNativeSampleTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = MaterialTheme.colorScheme.primary,
            secondary = MaterialTheme.colorScheme.secondary,
            background = MaterialTheme.colorScheme.background
        ),
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current

    // State for each ad type
    var headlineAd by remember { mutableStateOf<NativeAd?>(null) }
    var smallAd by remember { mutableStateOf<NativeAd?>(null) }
    var iconSmallAd by remember { mutableStateOf<NativeAd?>(null) }
    var mediumAd by remember { mutableStateOf<NativeAd?>(null) }
    var largeAd by remember { mutableStateOf<NativeAd?>(null) }
    var fullWidthMediaAd by remember { mutableStateOf<NativeAd?>(null) }
    var contentAd by remember { mutableStateOf<NativeAd?>(null) }
    var appInstallAd by remember { mutableStateOf<NativeAd?>(null) }

    var headlineLoading by remember { mutableStateOf(true) }
    var smallLoading by remember { mutableStateOf(true) }
    var iconSmallLoading by remember { mutableStateOf(true) }
    var mediumLoading by remember { mutableStateOf(true) }
    var largeLoading by remember { mutableStateOf(true) }
    var fullWidthMediaLoading by remember { mutableStateOf(true) }
    var contentLoading by remember { mutableStateOf(true) }
    var appInstallLoading by remember { mutableStateOf(true) }

    var headlineError by remember { mutableStateOf<String?>(null) }
    var smallError by remember { mutableStateOf<String?>(null) }
    var iconSmallError by remember { mutableStateOf<String?>(null) }
    var mediumError by remember { mutableStateOf<String?>(null) }
    var largeError by remember { mutableStateOf<String?>(null) }
    var fullWidthMediaError by remember { mutableStateOf<String?>(null) }
    var contentError by remember { mutableStateOf<String?>(null) }
    var appInstallError by remember { mutableStateOf<String?>(null) }

    // Test ad unit ID for native ads
    val testAdUnitId = "ca-app-pub-3940256099942544/2247696110"

    // Load headline ad
    LaunchedEffect(Unit) {
        val adLoader = AdLoader.Builder(context, testAdUnitId)
            .forNativeAd { ad ->
                headlineAd = ad
                headlineLoading = false
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    headlineLoading = false
                    headlineError = error.message
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

    // Load small ad
    LaunchedEffect(Unit) {
        val adLoader = AdLoader.Builder(context, testAdUnitId)
            .forNativeAd { ad ->
                smallAd = ad
                smallLoading = false
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    smallLoading = false
                    smallError = error.message
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

    // Load icon small ad
    LaunchedEffect(Unit) {
        val adLoader = AdLoader.Builder(context, testAdUnitId)
            .forNativeAd { ad ->
                iconSmallAd = ad
                iconSmallLoading = false
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    iconSmallLoading = false
                    iconSmallError = error.message
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

    // Load medium ad
    LaunchedEffect(Unit) {
        val adLoader = AdLoader.Builder(context, testAdUnitId)
            .forNativeAd { ad ->
                mediumAd = ad
                mediumLoading = false
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    mediumLoading = false
                    mediumError = error.message
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

    // Load large ad
    LaunchedEffect(Unit) {
        val adLoader = AdLoader.Builder(context, testAdUnitId)
            .forNativeAd { ad ->
                largeAd = ad
                largeLoading = false
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    largeLoading = false
                    largeError = error.message
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

    // Load full width media ad (CTR Optimized)
    LaunchedEffect(Unit) {
        val adLoader = AdLoader.Builder(context, testAdUnitId)
            .forNativeAd { ad ->
                fullWidthMediaAd = ad
                fullWidthMediaLoading = false
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    fullWidthMediaLoading = false
                    fullWidthMediaError = error.message
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

    // Load content ad (CTR Optimized)
    LaunchedEffect(Unit) {
        val adLoader = AdLoader.Builder(context, testAdUnitId)
            .forNativeAd { ad ->
                contentAd = ad
                contentLoading = false
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    contentLoading = false
                    contentError = error.message
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

    // Load app install ad (CTR Optimized)
    LaunchedEffect(Unit) {
        val adLoader = AdLoader.Builder(context, testAdUnitId)
            .forNativeAd { ad ->
                appInstallAd = ad
                appInstallLoading = false
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    appInstallLoading = false
                    appInstallError = error.message
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

    // Clean up ads when leaving composition
    DisposableEffect(Unit) {
        onDispose {
            headlineAd?.destroy()
            smallAd?.destroy()
            iconSmallAd?.destroy()
            mediumAd?.destroy()
            largeAd?.destroy()
            fullWidthMediaAd?.destroy()
            contentAd?.destroy()
            appInstallAd?.destroy()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "AdMob Native Ad Templates",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // CTR Optimized Section Header
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF4CAF50).copy(alpha = 0.1f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            "CTR Optimized Templates",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32)
                        )
                        Text(
                            "High-performance ad templates designed for maximum click-through rates",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF388E3C)
                        )
                    }
                }
            }

            // Full Width Media Template (CTR Optimized)
            item {
                AdSection(
                    title = "Full Width Media Template",
                    description = "High-impact layout with large media, gradient overlay, and prominent CTA. Perfect for hero placements.",
                    isLoading = fullWidthMediaLoading,
                    error = fullWidthMediaError,
                    badge = "CTR+"
                ) {
                    Column {
                        NativeAdFullWidthMediaBox(
                            nativeAd = fullWidthMediaAd,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Dark CTA variant
                        NativeAdFullWidthMediaBox(
                            nativeAd = fullWidthMediaAd,
                            ctaButtonColor = Color(0xFF4CAF50),
                            ctaTextColor = Color.White,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // Content Template (CTR Optimized)
            item {
                AdSection(
                    title = "Content Feed Template",
                    description = "Social media style layout that blends naturally with content feeds. Reduces ad blindness.",
                    isLoading = contentLoading,
                    error = contentError,
                    badge = "CTR+"
                ) {
                    Column {
                        NativeAdContentBox(
                            nativeAd = contentAd,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Auto color variant
                        NativeAdAutoColorWrapper(
                            nativeAd = contentAd,
                            modifier = Modifier.fillMaxWidth()
                        ) { backgroundColor, textColor ->
                            NativeAdContentBox(
                                nativeAd = contentAd,
                                backgroundColor = backgroundColor
                                    ?: MaterialTheme.colorScheme.surface,
                                textColor = textColor ?: MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                            )
                        }
                    }
                }
            }

            // App Install Template (CTR Optimized)
            item {
                AdSection(
                    title = "App Install Template",
                    description = "App Store style layout optimized for app promotions. Features rating, price, and prominent install button.",
                    isLoading = appInstallLoading,
                    error = appInstallError,
                    badge = "CTR+"
                ) {
                    Column {
                        NativeAdAppInstallBox(
                            nativeAd = appInstallAd,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Green CTA variant
                        NativeAdAppInstallBox(
                            nativeAd = appInstallAd,
                            ctaButtonColor = Color(0xFF4CAF50),
                            ctaTextColor = Color.White,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // Standard Templates Section Header
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Standard Templates",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Headline Template
            item {
                AdSection(
                    title = "Headline Template",
                    description = "Ultra-compact layout perfect for headers and tight spaces",
                    isLoading = headlineLoading,
                    error = headlineError
                ) {
                    NativeAdHeadlineBox(
                        nativeAd = headlineAd
                    )
                }
            }

            // Small Template
            item {
                AdSection(
                    title = "Small Template",
                    description = "Compact horizontal layout ideal for list items",
                    isLoading = smallLoading,
                    error = smallError
                ) {

                    Column {

                        NativeAdSmallBox(
                            nativeAd = smallAd,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        NativeAdAutoColorWrapper(
                            nativeAd = headlineAd,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .clip(
                                    RoundedCornerShape(8.dp)
                                )
                        ) { backgroundColor: Color?, textColor: Color? ->
                            NativeAdSmallBox(
                                nativeAd = smallAd,
                                backgroundColor = backgroundColor
                                    ?: MaterialTheme.colorScheme.surfaceVariant,
                                textColor = textColor ?: MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                            )

                        }
                    }

                }
            }

            // Icon Small Template
            item {
                AdSection(
                    title = "Icon Small Template",
                    description = "Compact layout with icon focus, perfect for content feeds",
                    isLoading = iconSmallLoading,
                    error = iconSmallError
                ) {
                    Column {
                        NativeAdIconSmallBox(
                            nativeAd = iconSmallAd,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        NativeAdAutoColorWrapper(
                            nativeAd = iconSmallAd,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .clip(RoundedCornerShape(12.dp))
                        ) { backgroundColor, textColor ->
                            NativeAdIconSmallBox(
                                nativeAd = iconSmallAd,
                                backgroundColor = backgroundColor
                                    ?: MaterialTheme.colorScheme.surfaceVariant,
                                textColor = textColor ?: MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                            )
                        }
                    }
                }
            }

            // Medium Template
            item {
                AdSection(
                    title = "Medium Template",
                    description = "Full-featured card layout for prominent placements",
                    isLoading = mediumLoading,
                    error = mediumError
                ) {
                    Column {
                        NativeAdMediumBox(
                            nativeAd = mediumAd,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        NativeAdAutoColorWrapper(
                            nativeAd = mediumAd,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .clip(RoundedCornerShape(16.dp))
                        ) { backgroundColor, textColor ->
                            NativeAdMediumBox(
                                nativeAd = mediumAd,
                                backgroundColor = backgroundColor
                                    ?: MaterialTheme.colorScheme.surfaceVariant,
                                textColor = textColor ?: MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                            )
                        }
                    }
                }
            }

            // Large Template (CTR Optimized)
            item {
                AdSection(
                    title = "Large Template",
                    description = "Premium layout with large media, star rating, and prominent CTA button for maximum click-through rates",
                    isLoading = largeLoading,
                    error = largeError
                ) {
                    Column {
                        NativeAdLargeBox(
                            nativeAd = largeAd,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        NativeAdAutoColorWrapper(
                            nativeAd = largeAd,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .clip(RoundedCornerShape(12.dp))
                        ) { backgroundColor, textColor ->
                            NativeAdLargeBox(
                                nativeAd = largeAd,
                                backgroundColor = backgroundColor
                                    ?: MaterialTheme.colorScheme.surfaceVariant,
                                textColor = textColor ?: MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                            )
                        }
                    }
                }
            }

            // Info section
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            "Information",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            "This sample app uses Google's test ad unit IDs. " +
                                    "These ads are safe for testing and won't generate revenue.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Test Ad Unit ID:",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            testAdUnitId,
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AdSection(
    title: String,
    description: String,
    isLoading: Boolean,
    error: String?,
    badge: String? = null,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        androidx.compose.foundation.layout.Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            if (badge != null) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF4CAF50)
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = badge,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator()
                }

                error != null -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            "Failed to load ad",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                        Text(
                            error,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                else -> {
                    content()
                }
            }
        }

    }
}
