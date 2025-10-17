# AdMob Native Compose

A Jetpack Compose library for AdMob Native Ad components. It supports Material 3 theming and has been separated into a reusable library for various projects.

## Features

- ✅ Full Jetpack Compose support
- ✅ Material 3 theme integration
- ✅ Customizable background and text colors
- ✅ Small/Medium template layouts
- ✅ ViewBinding-based implementation

## Installation

### 1. Add module to settings.gradle.kts

```kotlin
include(":admob-native-compose")
```

### 2. Add dependency to app/build.gradle.kts

```kotlin
dependencies {
   implementation(project(":admob-native-compose"))
}
```

## Usage

### Basic Usage Example

```kotlin
import com.soosu.admobnative.NativeAdSmallBox
import androidx.compose.material3.MaterialTheme

@Composable
fun MyScreen() {
   val nativeAd: NativeAd? = // ... Load AdMob Native Ad

      Box(
         modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(8.dp))
      ) {
         NativeAdSmallBox(
            nativeAd = nativeAd,
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            textColor = MaterialTheme.colorScheme.onBackground
         )
      }
}
```

### NativeAdSmallBox Parameters

- **nativeAd**: `NativeAd?` - AdMob Native Ad object
- **modifier**: `Modifier` - Compose Modifier (default: `Modifier`)
- **backgroundColor**: `Color` - Ad background color (default: `MaterialTheme.colorScheme.surfaceVariant`)
- **textColor**: `Color` - Text color (default: `MaterialTheme.colorScheme.onBackground`)

### Native Ad Loading Example

```kotlin
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions

var nativeAd by remember { mutableStateOf<NativeAd?>(null) }

LaunchedEffect(Unit) {
    val adLoader = AdLoader.Builder(context, "YOUR_AD_UNIT_ID")
        .forNativeAd { ad ->
            nativeAd = ad
        }
        .withNativeAdOptions(
            NativeAdOptions.Builder()
                .setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_RIGHT)
                .build()
        )
        .build()

    adLoader.loadAd(AdRequest.Builder().build())
}

 Box(
    modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .clip(RoundedCornerShape(8.dp))
) {
    NativeAdSmallBox(nativeAd = nativeAd)
}

```

## Template Layouts

The library provides two templates:

1. **Small Template** (`gnt_ad_small_template_view.xml`)
<img width="459" height="119" alt="스크린샷 2025-10-17 오후 6 22 45" src="https://github.com/user-attachments/assets/9e8aa35f-73f5-4a48-847b-907b7b426e5b" />

   - Displays headline and advertiser information with a small image
   - Compact layout


2. **Medium Template** (`gnt_ad_medium_template_view.xml`)
<img width="457" height="356" alt="스크린샷 2025-10-17 오후 6 21 21" src="https://github.com/user-attachments/assets/df541d69-5c7b-4f2d-b1b8-12d82052fb5d" />
   
   - Displays headline and advertiser information with a large image
   - Layout that takes up more space

3. **Headline Template** (`gnt_ad_headline_template_view.xml`)
<img width="432" height="46" alt="스크린샷 2025-10-17 오후 10 46 35" src="https://github.com/user-attachments/assets/daf1f09b-a2b3-41e3-bfdf-1463b6e256bc" />


   
Each template is used in the `NativeAdSmallBox` and `NativeAdMediumBox` and `NativeAdHeadlineBox`composables.

## Dependencies

This library uses the following dependencies:

- Jetpack Compose BOM
- Material 3
- Google Play Services Ads
- AndroidX ConstraintLayout
- AndroidX CardView

## License

This library follows the original project license.

## Contributing

Please submit bug reports or feature suggestions as issues.
