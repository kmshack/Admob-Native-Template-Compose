# AdMob Native Compose

Jetpack Compose용 AdMob Native Ad 컴포넌트 라이브러리입니다. Material 3 테마를 지원하며, 다양한 프로젝트에서 재사용할 수 있도록 분리되었습니다.

## 기능

- ✅ Jetpack Compose 완벽 지원
- ✅ Material 3 테마 통합
- ✅ 배경색 및 텍스트 색상 커스터마이징
- ✅ Small/Medium 템플릿 레이아웃 제공
- ✅ ViewBinding 기반 구현

## 설치

### 1. settings.gradle.kts에 모듈 추가

```kotlin
include(":admob-native-compose")
```

### 2. app/build.gradle.kts에 의존성 추가

```kotlin
dependencies {
    implementation(project(":admob-native-compose"))
}
```

## 사용법

### 기본 사용 예제

```kotlin
import com.soosu.admobnative.NativeAdSmallBox
import androidx.compose.material3.MaterialTheme

@Composable
fun MyScreen() {
    val nativeAd: NativeAd? = // ... AdMob Native Ad 로드

       NativeAdSmallBox(
        nativeAd = nativeAd,
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
        textColor = MaterialTheme.colorScheme.onBackground
    )
}
```

### NativeAdSmallBox 파라미터

- **nativeAd**: `NativeAd?` - AdMob Native Ad 객체
- **modifier**: `Modifier` - Compose Modifier (기본값: `Modifier`)
- **backgroundColor**: `Color` - 광고 배경색 (기본값: `MaterialTheme.colorScheme.surfaceVariant`)
- **textColor**: `Color` - 텍스트 색상 (기본값: `MaterialTheme.colorScheme.onBackground`)

### Native Ad 로드 예제

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

NativeAdSmallBox(nativeAd = nativeAd)
```

## 템플릿 레이아웃

라이브러리는 두 가지 템플릿을 제공합니다:

1. **Small Template** (`gnt_ad_small_white_image_template_view.xml`)
   - 작은 이미지와 함께 헤드라인, 광고주 정보 표시
   - 컴팩트한 레이아웃

2. **Medium Template** (`gnt_ad_medium_white_image_template_view.xml`)
   - 큰 이미지와 함께 헤드라인, 광고주 정보 표시
   - 더 많은 공간을 차지하는 레이아웃
   - 
각 템플릿은 `NativeAdSmallBox` 및 `NativeAdMediumBox` 컴포저블에서 사용됩니다.

## 의존성

이 라이브러리는 다음 의존성을 사용합니다:

- Jetpack Compose BOM
- Material 3
- Google Play Services Ads
- AndroidX ConstraintLayout
- AndroidX CardView

## 라이선스

이 라이브러리는 원본 프로젝트 라이선스를 따릅니다.

## 기여

버그 리포트나 기능 제안은 이슈로 등록해주세요.
