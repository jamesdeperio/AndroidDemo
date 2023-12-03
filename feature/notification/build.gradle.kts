plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}


kapt {
    correctErrorTypes = true
}
hilt {
    enableAggregatingTask = true
}

android {
    namespace = "${AndroidConfiguration.NAMESPACE}.notification"
    compileSdk = AndroidConfiguration.COMPILED_SDK

    defaultConfig {
        minSdk = AndroidConfiguration.MIN_SDK_VERSION
        targetSdk = AndroidConfiguration.TARGET_SDK_VERSION
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
    }
}

dependencies {

    implementation(project(Module.DESIGN))
    implementation(project(Module.COMMON))
    //UI
    implementation(Dependency.UI.EPOXY)
    annotationProcessor(Dependency.UI.EPOXY_PROCESSOR)
    kapt(Dependency.UI.EPOXY_PROCESSOR)
    
    implementation(Dependency.Core.KTX_LIFECYCLE_LIVEDATA)
    implementation(Dependency.Core.KTX_ACTIVITY)
    implementation(Dependency.Core.KTX_LIFECYCLE_VIEWMODEL)
    implementation(Dependency.Core.CONSTRAINT_LAYOUT)
    implementation(Dependency.Core.KTX_APPCOMPAT)
    implementation(Dependency.Core.GOOGLE_MATERIAL)
    implementation(Dependency.Core.CONSTRAINT_LAYOUT)


    implementation(Dependency.RX.RXJAVA)
    implementation(Dependency.RX.RXANDROID)
    implementation(Dependency.RX.RXKOTLIN)

    implementation(Dependency.Network.RETROFIT)
    implementation(Dependency.Network.RETROFITKIT)
    implementation(Dependency.Network.RETROFIT_GSON)
    implementation(Dependency.Network.RETROFIT_RX)
    implementation(Dependency.Network.OKHTTP3_LOGGING)
    implementation(Dependency.Network.TIKXML)

    implementation(Dependency.Injection.DAGGER)
    kapt(Dependency.Injection.DAGGER_COMPILER)

    testImplementation(Dependency.Core.JUNIT)
    androidTestImplementation(Dependency.Core.KTX_JUNIT_EXT)
    androidTestImplementation(Dependency.Core.KTX_ESPRESSO)
}