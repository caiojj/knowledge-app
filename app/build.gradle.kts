plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android")
}

android {
    compileSdk = 31

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "br.com.knowledge"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN}")
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")

    // https://github.com/bumptech/glide
    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")

    //retrofit @see https://square.github.io/retrofit/
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

    //koin @see https://insert-koin.io/docs/quickstart/android/
    implementation("io.insert-koin:koin-android:3.1.2")
    implementation("io.insert-koin:koin-android-ext:3.0.1")

    //coroutines @see https://developer.android.com/kotlin/coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1")

    //lifecycle @see https://developer.android.com/topic/libraries/architecture/lifecycle?hl=pt-Br#lc
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")

    //room @see https://developer.android.com/training/data-storage/room
    implementation("androidx.room:room-runtime:2.3.0")
    implementation("androidx.room:room-ktx:2.3.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt("androidx.room:room-compiler:2.3.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}