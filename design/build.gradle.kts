plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "${AndroidConfiguration.NAMESPACE}.design"
    compileSdk = AndroidConfiguration.COMPILED_SDK

    defaultConfig {
        minSdk = AndroidConfiguration.MIN_SDK_VERSION
        targetSdk = AndroidConfiguration.TARGET_SDK_VERSION
        vectorDrawables.useSupportLibrary = true
    }

    buildFeatures {
        viewBinding = true
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
    //CORE LIBRARIES
    implementation(project(Module.COMMON))
    implementation(Dependency.Core.KTX_CORE)
    implementation(Dependency.Core.KTX_CORE_SPLASH)
    implementation(Dependency.Core.CONSTRAINT_LAYOUT)
    implementation(Dependency.Core.GOOGLE_MATERIAL)


    //UI
    implementation(Dependency.UI.EPOXY)
    annotationProcessor(Dependency.UI.EPOXY_PROCESSOR)
    kapt(Dependency.UI.EPOXY_PROCESSOR)

    //image processor
    implementation(Dependency.UI.GLIDE)
    kapt (Dependency.UI.GLIDE_COMPILER)
    implementation (Dependency.UI.ANDROID_SVG)
    implementation (Dependency.UI.LOTTIE)

    //anim
    implementation(Dependency.UI.ANDROID_ANIM)
    implementation(Dependency.UI.RECYCLERVIEW_ANIM)
    implementation(Dependency.UI.FB_SHIMMER)
    
    //rx
    implementation(Dependency.RX.RXJAVA)
    implementation(Dependency.RX.RXANDROID)
    implementation(Dependency.RX.RXKOTLIN)

}

kapt {
    correctErrorTypes = true
}