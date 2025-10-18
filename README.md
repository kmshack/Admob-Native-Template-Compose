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
- 📱 **Multiple Templates** - Small, Medium, and Headline layouts included
- ⚡ **Type-Safe** - Fully written in Kotlin with null safety
- 🔧 **Highly Customizable** - Override colors, modifiers, and styling
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
    implementation("com.github.kmshack:Admob-Native-Template-Compose:1.0.5")
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

The library provides three pre-built templates optimized for different use cases:

### 1. Small Template - `NativeAdSmallBox`

<img width="459" height="119" alt="Small Template Preview" src="https://github.com/user-attachments/assets/9e8aa35f-73f5-4a48-847b-907b7b426e5b" />

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

<img width="457" height="356" alt="Medium Template Preview" src="https://github.com/user-attachments/assets/df541d69-5c7b-4f2d-b1b8-12d82052fb5d" />

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

<img width="432" height="46" alt="Headline Template Preview" src="https://github.com/user-attachments/assets/daf1f09b-a2b3-41e3-bfdf-1463b6e256bc" />

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

- ✅ **All Three Templates** - Headline, Small, and Medium layouts
- ✅ **Live Ad Loading** - Using Google's test ad unit IDs
- ✅ **Loading States** - Progress indicators while ads load
- ✅ **Error Handling** - Graceful error messages when ads fail
- ✅ **Material 3 Theming** - Modern, beautiful UI design
- ✅ **Lifecycle Management** - Proper ad cleanup on dispose
- ✅ **Best Practices** - Production-ready implementation patterns

### Screenshots

The sample app displays all three template types in a scrollable list with detailed descriptions for each layout.

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
| Google Play Services Ads | 24.4.0 | AdMob SDK |
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
