plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.group12"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.group12"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }

}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("androidx.test.uiautomator:uiautomator:2.2.0")
    implementation("com.google.androidbrowserhelper:billing:1.0.0-alpha10")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    testImplementation("junit:junit:4.13.2")
    implementation("com.google.android.gms:play-services-location:21.1.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    implementation("com.firebaseui:firebase-ui-database:8.0.0")
    implementation ("com.paypal.sdk:paypal-android-sdk:2.16.0")
    implementation ("com.github.blackfizz:eazegraph:1.2.5l@aar")
    implementation ("com.nineoldandroids:library:2.4.0")

}