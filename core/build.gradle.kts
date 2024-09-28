plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

apply("../shared_dependencies.gradle")

android {
    namespace = "com.bangkit2024.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField(
            "String",
            "BASE_URL",
            "\"https://api.themoviedb.org/3/\""
        )

        buildConfigField(
            "String",
            "API_KEY",
            "\"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1NDIzYjE4MTJmMWY4ZmEzZDU2YjQxMDM3MGUzNzBhZCIsIm5iZiI6MTcyNDQ2NTE2OC45MTQ0MTcsInN1YiI6IjY2YzU1N2I0ODRjNzYyMzlmODJkNGIyMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.xIR0HZKPXFIPkl11aJ07WZDBLunng8OPxYz5DAuSbfQ\""
        )

        buildConfigField(
            "String",
            "IMAGE_URL",
            "\"https://image.tmdb.org/t/p/original\""
        )

        buildConfigField(
            "String",
            "HOSTNAME",
            "\"api.themoviedb.org\""
        )

        buildConfigField(
            "String",
            "CERTIFICATE_PINNER_1",
            "\"sha256/k1Hdw5sdSn5kh/gemLVSQD/P4i4IBQEY1tW4WNxh9XM=\""
        )

        buildConfigField(
            "String",
            "CERTIFICATE_PINNER_2",
            "\"sha256/18tkPyr2nckv4fgo0dhAkaUtJ2hu2831xlO2SKhq8dg=\""
        )

        buildConfigField(
            "String",
            "CERTIFICATE_PINNER_3",
            "\"sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=\""
        )
    }

    buildTypes {
        debug {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    // Networking (Retrofit, GSON Converter, Logging OkHTTP)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Room Security
    implementation(libs.android.database.sqlcipher)
    implementation(libs.androidx.sqlite.ktx)
}