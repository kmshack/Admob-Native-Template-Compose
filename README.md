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
- [AI Integration Guide](#ai-integration-guide)
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
    implementation("com.github.kmshack:Admob-Native-Template-Compose:1.1.5")
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

## AI Integration Guide

Use AI assistants (ChatGPT, Claude, Gemini, etc.) to quickly integrate AdMob Native Template Compose into your project. This guide provides optimized prompts for various integration scenarios.

### 🤖 Why Use AI for Integration?

- ⚡ **Faster Setup** - Get working code in seconds
- 🎯 **Context-Aware** - AI understands your specific use case
- 🔧 **Customization** - Easy to modify based on your requirements
- 📚 **Learning** - Understand best practices through AI explanations

---

### 📝 Prompt Writing Best Practices

When asking AI to help with this library:

1. **Be Specific**: Mention the exact template you want (Small, Medium, Large, Headline)
2. **Include Context**: Share your use case (e.g., "in a LazyColumn feed")
3. **Specify Requirements**: Mention any customization needs (colors, modifiers, etc.)
4. **Request Explanations**: Ask AI to explain the code for learning

---

### 🚀 Quick Start Prompts

#### Basic Integration

```
I want to integrate AdMob Native ads in my Jetpack Compose app using the
"Admob-Native-Template-Compose" library (version 1.1.5).

Please provide:
1. Complete build.gradle.kts setup including JitPack repository
2. AndroidManifest.xml configuration for AdMob
3. A Composable function that loads and displays a native ad using NativeAdSmallBox
4. Proper error handling and loading states

My project uses:
- Kotlin 2.1.0
- Jetpack Compose BOM 2025.06.00
- Material 3
```

#### Template-Specific Prompts

**Small Template (Compact Lists)**
```
I need to show native ads in my LazyColumn list using NativeAdSmallBox from
Admob-Native-Template-Compose library.

Requirements:
- Show an ad every 5 items in the list
- Include loading placeholder while ad loads
- Match my app's color scheme (background: #F5F5F5, text: #333333)
- Clean up ads properly when items are removed

Please provide complete implementation with ViewModel if needed.
```

**Medium Template (Content Cards)**
```
Create a content card screen with NativeAdMediumBox from Admob-Native-Template-Compose.

Requirements:
- Display ad between news articles
- Add rounded corners (16dp) and shadow elevation
- Show CircularProgressIndicator while loading
- Implement proper ad lifecycle management with DisposableEffect

Use Material 3 design and include dark mode support.
```

**Large Template (Premium Placement)**
```
I want to implement a high-CTR ad placement using NativeAdLargeBox from
Admob-Native-Template-Compose library.

Requirements:
- Full-screen ad between app sections
- Customize CTA button color to #1976D2
- Enable star rating and price display
- Add auto color extraction using NativeAdAutoColorWrapper
- Implement proper memory cleanup

Please include detailed comments explaining each part.
```

**Headline Template (Minimal Banner)**
```
Add a minimal ad banner using NativeAdHeadlineBox from Admob-Native-Template-Compose
at the bottom of my screen.

Requirements:
- Fixed position at bottom (not scrollable)
- Transparent background to match app theme
- Centered horizontally
- Only show when ad is loaded (no placeholder)
```

---

### 🎨 Customization Prompts

#### Color Theming
```
I'm using NativeAdMediumBox from Admob-Native-Template-Compose and need to customize
colors to match my brand.

Brand colors:
- Primary: #6200EE
- Background: #FFFFFF
- Surface: #F5F5F5
- OnSurface: #000000

Please show how to:
1. Apply these colors to the ad template
2. Ensure proper contrast for readability
3. Support both light and dark mode
```

#### Auto Color Extraction
```
Show me how to implement NativeAdAutoColorWrapper from Admob-Native-Template-Compose
to automatically extract colors from ad images.

Requirements:
- Use with NativeAdLargeBox
- Fallback to Material 3 theme colors if extraction fails
- Animate color transitions smoothly
- Include explanation of how Palette API works
```

#### Advanced Modifiers
```
I want to enhance my NativeAdMediumBox with advanced Compose modifiers.

Please show:
1. Rounded corners with gradient border
2. Subtle shadow and elevation
3. Click ripple effect on entire card
4. Smooth fade-in animation when ad loads
5. Horizontal padding for tablet/desktop screens

Use modern Compose best practices.
```

---

### 🔧 Architecture Prompts

#### MVVM Pattern
```
Create a complete MVVM architecture for managing AdMob native ads using
Admob-Native-Template-Compose library.

Include:
- AdViewModel to handle ad loading logic
- Sealed class for ad states (Loading, Success, Error)
- Repository pattern for ad requests
- Composable screen that observes ViewModel
- Use NativeAdSmallBox template
- Proper dependency injection setup (Hilt)

Explain the architecture decisions.
```

#### Multi-Template Screen
```
Build a demo screen showing all four templates (Headline, Small, Medium, Large)
from Admob-Native-Template-Compose in a scrollable list.

Requirements:
- LazyColumn with different ad templates
- Each template loads independently
- Section headers explaining each template
- Loading shimmer effect for each ad type
- Error handling per template
- Material 3 design with proper spacing

Include detailed comments.
```

---

### 🐛 Troubleshooting Prompts

#### Ad Not Showing
```
My NativeAdSmallBox from Admob-Native-Template-Compose is not displaying.

Current setup:
[paste your code here]

AdMob App ID: ca-app-pub-xxxxxxxx~yyyyyy
Ad Unit ID: ca-app-pub-xxxxxxxx/zzzzzzzz

Please help debug:
1. Check manifest configuration
2. Verify ad loading code
3. Add proper logging to see ad lifecycle
4. Suggest test ad unit IDs for development
5. Check for common mistakes
```

#### Memory Leaks
```
I'm experiencing memory leaks with AdMob native ads using Admob-Native-Template-Compose.

My current implementation:
[paste your code]

Please show:
1. Proper use of DisposableEffect for cleanup
2. When to call nativeAd.destroy()
3. How to handle configuration changes
4. ViewModel integration for lifecycle management
5. Memory profiling tips specific to native ads
```

#### Build/Dependency Issues
```
I'm getting build errors when adding Admob-Native-Template-Compose library.

Error message:
[paste error here]

My build.gradle.kts:
[paste relevant parts]

Please help:
1. Verify JitPack repository setup
2. Check for dependency conflicts
3. Suggest version catalog approach
4. Fix any Kotlin/Compose version mismatches
```

---

### 💡 Learning Prompts

```
I'm new to AdMob native ads with Jetpack Compose. Using the Admob-Native-Template-Compose
library, please explain:

1. How native ads differ from banner/interstitial ads
2. The lifecycle of a native ad in Compose
3. Best practices for ad placement for maximum CTR
4. How to test ads during development
5. Common pitfalls to avoid

Include a beginner-friendly example using NativeAdSmallBox with detailed comments.
```

---

### 📋 Complete Project Setup Prompt

```
I'm starting a new Android project and want to integrate AdMob native ads using
Admob-Native-Template-Compose library from scratch.

Project requirements:
- Modern Android app with Jetpack Compose
- MVVM architecture with Hilt
- Material 3 design system
- Show native ads in a news feed (LazyColumn)
- Use NativeAdMediumBox template
- Support dark mode
- Proper error handling and logging

Please provide:
1. Complete project structure overview
2. All necessary gradle files (build.gradle.kts, settings.gradle.kts)
3. AndroidManifest.xml configuration
4. AdViewModel with StateFlow
5. Repository for ad loading
6. Main screen composable with LazyColumn
7. Test ad unit IDs for development
8. Comments explaining each decision

Make it production-ready and follow Android best practices.
```

---

### 🎯 Tips for Best Results

1. **Copy Library Code**: Include relevant code snippets from this README in your prompt
2. **Version Specific**: Always mention library version (1.1.5) to get accurate syntax
3. **Be Iterative**: Start with basic implementation, then ask for enhancements
4. **Request Tests**: Ask AI to generate unit tests for your ad integration
5. **Ask Why**: Request explanations to understand the implementation
6. **Share Errors**: If something doesn't work, share exact error messages
7. **Update Context**: Inform AI about your Kotlin/Compose versions

---

### 🔗 Useful Information to Share with AI

When asking for help, provide these details for better responses:

```
Library: Admob-Native-Template-Compose
Version: 1.1.3
GitHub: https://github.com/kmshack/Admob-Native-Template-Compose
JitPack: https://jitpack.io/#kmshack/Admob-Native-Template-Compose

Available Templates:
- NativeAdHeadlineBox (minimal, compact)
- NativeAdSmallBox (list items)
- NativeAdMediumBox (content cards)
- NativeAdLargeBox (premium, high CTR)

Key Features:
- Material 3 integration
- Auto color extraction (NativeAdAutoColorWrapper)
- Fully customizable (backgroundColor, textColor, modifiers)
- Built for Jetpack Compose

My Tech Stack:
- Kotlin: [your version]
- Compose BOM: [your version]
- Min SDK: [your min SDK]
- Architecture: [MVVM/MVI/etc]
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
