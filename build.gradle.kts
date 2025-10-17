plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("maven-publish")
}

android {
    namespace = "com.soosu.admobnative"
    compileSdk = 36

    defaultConfig {
        minSdk = 25

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }
}

dependencies {
    // Compose BOM
    val composeBom = platform("androidx.compose:compose-bom:2025.06.00")
    implementation(composeBom)

    // Compose
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-viewbinding")

    // Google Play Services Ads
    implementation("com.google.android.gms:play-services-ads:24.4.0")

    // Core
    implementation("androidx.core:core-ktx:1.16.0")

    // ConstraintLayout and CardView for XML layouts
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("androidx.cardview:cardview:1.0.0")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = "com.github.kmshack"
                artifactId = "admob-native-template-compose"
                version = "1.0.2"

                pom {
                    name.set("Admob Native Template Compose")
                    description.set("AdMob Native Ad templates for Jetpack Compose")
                    url.set("https://github.com/kmshack/Admob-Native-Template-Compose")

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }

                    developers {
                        developer {
                            id.set("kmshack")
                            name.set("kmshack")
                        }
                    }

                    scm {
                        connection.set("scm:git:github.com/kmshack/Admob-Native-Template-Compose.git")
                        developerConnection.set("scm:git:ssh://github.com/kmshack/Admob-Native-Template-Compose.git")
                        url.set("https://github.com/kmshack/Admob-Native-Template-Compose")
                    }
                }
            }
        }
    }
}
