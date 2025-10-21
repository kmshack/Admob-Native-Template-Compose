package com.soosu.admobnative.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.soosu.admobnative.NativeAdHeadlineBox
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
    var mediumAd by remember { mutableStateOf<NativeAd?>(null) }
    var largeAd by remember { mutableStateOf<NativeAd?>(null) }

    var headlineLoading by remember { mutableStateOf(true) }
    var smallLoading by remember { mutableStateOf(true) }
    var mediumLoading by remember { mutableStateOf(true) }
    var largeLoading by remember { mutableStateOf(true) }

    var headlineError by remember { mutableStateOf<String?>(null) }
    var smallError by remember { mutableStateOf<String?>(null) }
    var mediumError by remember { mutableStateOf<String?>(null) }
    var largeError by remember { mutableStateOf<String?>(null) }

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

    // Clean up ads when leaving composition
    DisposableEffect(Unit) {
        onDispose {
            headlineAd?.destroy()
            smallAd?.destroy()
            mediumAd?.destroy()
            largeAd?.destroy()
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
            // Headline Template
            item {
                AdSection(
                    title = "Headline Template",
                    description = "Ultra-compact layout perfect for headers and tight spaces",
                    isLoading = headlineLoading,
                    error = headlineError
                ) {
                    NativeAdHeadlineBox(
                        nativeAd = headlineAd,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp)),
                        backgroundColor = MaterialTheme.colorScheme.surfaceVariant
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
                    NativeAdSmallBox(
                        nativeAd = smallAd,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                    )
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
                    NativeAdMediumBox(
                        nativeAd = mediumAd,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                    )
                }
            }

            // Large Template (CTR Optimized)
            item {
                AdSection(
                    title = "Large Template - CTR Optimized",
                    description = "Premium layout with large media, star rating, and prominent CTA button for maximum click-through rates",
                    isLoading = largeLoading,
                    error = largeError
                ) {
                    NativeAdLargeBox(
                        nativeAd = largeAd,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                    )
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
                            "ℹ️ Information",
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
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
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
                                "⚠️ Failed to load ad",
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
}
