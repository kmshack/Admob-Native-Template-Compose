# AdMob Native Template Compose

<div align="center">

**A modern, declarative AdMob Native Ads library for Jetpack Compose**

[![](https://jitpack.io/v/kmshack/Admob-Native-Template-Compose.svg)](https://jitpack.io/#kmshack/Admob-Native-Template-Compose)
[![API](https://img.shields.io/badge/API-25%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=25)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

</div>

---

## Overview

AdMob Native Template Compose provides ready-to-use, fully customizable native ad templates built specifically for Jetpack Compose. Seamlessly integrate Google AdMob native ads into your modern Android applications with Material 3 theming support and minimal boilerplate.

### Why This Library?

- 🚀 **Zero Boilerplate** - Drop-in composables with sensible defaults
- 🎨 **Material 3 Integration** - Automatically adapts to your app's theme
- 🌈 **Auto Color Extraction** - Intelligent color extraction from ad images for seamless integration
- 📱 **Multiple Templates** - Small, Medium, Large (CTR Optimized), and Headline layouts included
- ⚡ **Type-Safe** - Fully written in Kotlin with null safety
- 🔧 **Highly Customizable** - Override colors, modifiers, and styling
- 📈 **CTR Optimized** - Premium template designed for maximum click-through rates
- 📦 **Lightweight** - Minimal dependencies, maximum performance

---

## Table of Contents

- [Requirements](#requirements)
- [Installation](#installation)
- [Quick Start](#quick-start)
- [Available Templates](#available-templates)
- [API Reference](#api-reference)
- [Advanced Usage](#advanced-usage)
- [Sample App](#sample-app)
- [Dependencies](#dependencies)
- [Contributing](#contributing)
- [License](#license)

---

## Requirements

- **Minimum SDK**: 25 (Android 7.1)
- **Compile SDK**: 36+
- **Kotlin**: 1.9.0+
- **Jetpack Compose**: BOM 2025.06.00+
- **Google Play Services Ads**: 24.4.0+

---

## Installation

### Gradle Setup

**Step 1:** Add the JitPack repository to your `settings.gradle.kts` (or project-level `build.gradle`):

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```


**Step 2:** Add the dependency to your app module's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.kmshack:Admob-Native-Template-Compose:1.0.9")
}
```

**Step 3:** Sync your project

---

## Quick Start

### 1. Initialize AdMob

Add your AdMob App ID to `AndroidManifest.xml`:

```xml
<application>
    <meta-data
        android:name="com.google.android.gms.ads.APPLICATION_ID"
        android:value="ca-app-pub-xxxxxxxxxxxxxxxx~yyyyyyyyyy"/>
</application>
```

### 2. Load and Display a Native Ad

```kotlin
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.soosu.admobnative.NativeAdSmallBox

@Composable
fun MyScreen() {
    val context = LocalContext.current
    var nativeAd by remember { mutableStateOf<NativeAd?>(null) }

    LaunchedEffect(Unit) {
        val adLoader = AdLoader.Builder(context, "YOUR_AD_UNIT_ID")
            .forNativeAd { ad -> nativeAd = ad }
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    .setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_RIGHT)
                    .build()
            )
            .build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    // Display the ad
    NativeAdSmallBox(
        nativeAd = nativeAd,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}
```

---

## Available Templates

The library provides four pre-built templates optimized for different use cases:

### 1. Small Template - `NativeAdSmallBox`

<img width="625" height="354" alt="스크린샷 2025-11-01 오후 12 26 31" src="https://github.com/user-attachments/assets/4873d905-9b0f-4680-953c-fb72a8165e1d" />

**Best for:** List items, compact spaces, inline content

**Features:**
- Compact horizontal layout
- Small app icon with headline
- Advertiser name and CTA button
- Ideal for RecyclerView/LazyColumn items

```kotlin
NativeAdSmallBox(
    nativeAd = nativeAd,
    modifier = Modifier.fillMaxWidth()
)
```

### 2. Medium Template - `NativeAdMediumBox`

<img width="625" height="557" alt="스크린샷 2025-11-01 오후 12 26 59" src="https://github.com/user-attachments/assets/48c3c8ed-ece4-4e5b-8ed6-8bf2c571d21f" />

**Best for:** Cards, featured content, feed items

**Features:**
- Prominent media image (1200x628 recommended)
- Full headline and body text
- Advertiser branding
- Call-to-action button
- Perfect for news feeds or content cards

```kotlin
NativeAdMediumBox(
    nativeAd = nativeAd,
    modifier = Modifier.fillMaxWidth()
)
```

### 3. Headline Template - `NativeAdHeadlineBox`

<img width="524" height="75" alt="스크린샷 2025-11-01 오후 12 28 36" src="https://github.com/user-attachments/assets/fd962500-91ec-4329-a731-a7a4b2041573" />

**Best for:** Minimal spaces, headers, banners

**Features:**
- Ultra-compact design
- Headline only with small icon
- Minimal visual footprint
- Great for toolbars or between content sections

```kotlin
NativeAdHeadlineBox(
    nativeAd = nativeAd,
    modifier = Modifier.fillMaxWidth()
)
```

### 4. Large Template - `NativeAdLargeBox` 

<img width="532" height="561" alt="스크린샷 2025-11-01 오후 12 29 10" src="https://github.com/user-attachments/assets/b7f3c5f2-bc02-468a-96a5-e135bbdf46b7" />

**Best for:** Premium placements, maximum engagement, high CTR campaigns

**Features:**
- Large prominent media image (200dp height)
- Star rating display (when available)
- Price information display (when available)
- Premium CTA button with full width design
- Bold headline (2 lines)
- Detailed body text (3 lines)
- App icon and advertiser branding
- Optimized for maximum click-through rates

**Why This Template Boosts CTR:**
- **Visual Impact**: Large media image immediately captures attention
- **Trust Signals**: Star ratings and price information build credibility
- **Clear Action**: Prominent, full-width CTA button drives clicks
- **Information Hierarchy**: Well-structured layout guides users to action
- **Premium Feel**: Professional design increases perceived value

```kotlin
NativeAdLargeBox(
    nativeAd = nativeAd,
    modifier = Modifier.fillMaxWidth(),
    ctaButtonColor = Color(0xFF1976D2), // Customize CTA button color
    ctaTextColor = Color.White
)
```

---

## API Reference

### Common Parameters

All template composables share these parameters:

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `nativeAd` | `NativeAd?` | Required | The loaded AdMob native ad object |
| `modifier` | `Modifier` | `Modifier` | Compose modifier for layout customization |
| `backgroundColor` | `Color` | `MaterialTheme.colorScheme.surfaceVariant` | Background color of the ad container |
| `textColor` | `Color` | `MaterialTheme.colorScheme.onBackground` | Text color for ad content |

### Example: Custom Styling

```kotlin
NativeAdMediumBox(
    nativeAd = nativeAd,
    modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .shadow(4.dp),
    backgroundColor = Color(0xFFF5F5F5),
    textColor = Color(0xFF333333)
)
```

---

## Advanced Usage

### Handling Ad Load Lifecycle

```kotlin
@Composable
fun AdWithLoadingState() {
    val context = LocalContext.current
    var nativeAd by remember { mutableStateOf<NativeAd?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val adLoader = AdLoader.Builder(context, "YOUR_AD_UNIT_ID")
            .forNativeAd { ad ->
                nativeAd = ad
                isLoading = false
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    isLoading = false
                    isError = true
                }
            })
            .build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    when {
        isLoading -> CircularProgressIndicator()
        isError -> Text("Ad failed to load")
        else -> NativeAdSmallBox(nativeAd = nativeAd)
    }
}
```

### Dark Mode Support

The library automatically adapts to Material 3 theme changes:

```kotlin
MaterialTheme(
    colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
) {
    NativeAdMediumBox(
        nativeAd = nativeAd,
        // Automatically uses theme colors
    )
}
```

### Memory Management

Remember to destroy ads when the composable leaves the composition:

```kotlin
@Composable
fun AdWithCleanup() {
    var nativeAd by remember { mutableStateOf<NativeAd?>(null) }

    DisposableEffect(Unit) {
        onDispose {
            nativeAd?.destroy()
        }
    }

    NativeAdSmallBox(nativeAd = nativeAd)
}
```

### Auto Color Extraction

The library provides `NativeAdAutoColorWrapper` that automatically extracts dominant colors from ad images and applies them to the ad template. This creates a more cohesive, visually appealing ad experience.

**Features:**
- 🎨 Automatically extracts background color from ad images using Palette API
- 🔤 Calculates optimal text color (black/white) based on background brightness
- ⚡ Asynchronous color extraction on background thread
- 🔄 Works with all ad templates (Small, Medium, Large)
- 💡 Falls back to theme colors if extraction fails

**How it works:**
- Analyzes the ad's media image using the Palette library
- Extracts vibrant, muted, or dominant colors in priority order
- Calculates text color contrast using WCAG 2.0 luminance formula
- Provides nullable colors to the content composable

```kotlin
NativeAdAutoColorWrapper(
    nativeAd = nativeAd
) { backgroundColor, textColor ->
    NativeAdSmallBox(
        nativeAd = nativeAd,
        backgroundColor = backgroundColor ?: MaterialTheme.colorScheme.surfaceVariant,
        textColor = textColor ?: MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
    )
}
```

**Works with all templates:**

```kotlin
// Medium Template with auto colors
NativeAdAutoColorWrapper(nativeAd = nativeAd) { bgColor, txtColor ->
    NativeAdMediumBox(
        nativeAd = nativeAd,
        backgroundColor = bgColor ?: MaterialTheme.colorScheme.surfaceVariant,
        textColor = txtColor ?: MaterialTheme.colorScheme.onBackground
    )
}

// Large Template with auto colors
NativeAdAutoColorWrapper(nativeAd = nativeAd) { bgColor, txtColor ->
    NativeAdLargeBox(
        nativeAd = nativeAd,
        backgroundColor = bgColor ?: MaterialTheme.colorScheme.surfaceVariant,
        textColor = txtColor ?: MaterialTheme.colorScheme.onBackground
    )
}
```

**Why use Auto Color Extraction?**
- Creates a unified visual experience between ad content and your app
- Improves ad integration and reduces visual disruption
- Automatically adapts to different ad creatives
- Increases user engagement with better-looking ads

---

## Sample App

A complete sample application is included in this repository demonstrating all three ad templates.

### Running the Sample

```bash
# Clone the repository
git clone https://github.com/kmshack/Admob-Native-Template-Compose.git
cd Admob-Native-Template-Compose

# Build and run the sample app
./gradlew :sample:installDebug

# Or open in Android Studio and run the 'sample' module
```

### What's Included

The sample app demonstrates:

- ✅ **All Four Templates** - Headline, Small, Medium, and Large (CTR Optimized) layouts
- ✅ **Auto Color Extraction** - Live demonstration of automatic color extraction from ad images
- ✅ **Live Ad Loading** - Using Google's test ad unit IDs
- ✅ **Loading States** - Progress indicators while ads load
- ✅ **Error Handling** - Graceful error messages when ads fail
- ✅ **Material 3 Theming** - Modern, beautiful UI design
- ✅ **Lifecycle Management** - Proper ad cleanup on dispose
- ✅ **Best Practices** - Production-ready implementation patterns
- ✅ **CTR Optimization** - Premium template showcasing high-engagement design

### Screenshots

The sample app displays all four template types in a scrollable list with detailed descriptions for each layout.

### Test Ad Unit ID

The sample uses Google's official test ad unit:
```
ca-app-pub-3940256099942544/2247696110
```

**Note:** This is a test ID and will not generate revenue. Replace with your own ad unit ID for production use.

---

## Dependencies

This library uses the following dependencies:

| Dependency | Version | Purpose |
|------------|---------|---------|
| Jetpack Compose BOM | 2025.06.00 | Compose runtime and UI |
| Material 3 | 1.3.2+ | Material Design components |
| Google Play Services Ads | 24.7.0 | AdMob SDK |
| Palette KTX | 1.0.0 | Auto color extraction from images |
| AndroidX Core KTX | 1.16.0 | Core Android utilities |
| ConstraintLayout | 2.2.1 | Layout for XML templates |
| CardView | 1.0.0 | Card components |

---

## Contributing

Contributions are welcome! Here's how you can help:

1. **Report Bugs**: Open an issue with detailed reproduction steps
2. **Suggest Features**: Propose new templates or improvements
3. **Submit PRs**: Fork, create a feature branch, and submit a pull request

### Development Setup

```bash
git clone https://github.com/kmshack/Admob-Native-Template-Compose.git
cd Admob-Native-Template-Compose
./gradlew build
```

---

## License

```
Copyright 2025 kmshack

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

---

## Support

- **Issues**: [GitHub Issues](https://github.com/kmshack/Admob-Native-Template-Compose/issues)
- **Discussions**: [GitHub Discussions](https://github.com/kmshack/Admob-Native-Template-Compose/discussions)

---

<div align="center">

**Made with ❤️ for the Android community**

[⭐ Star this repo](https://github.com/kmshack/Admob-Native-Template-Compose) if you find it useful!

</div>
