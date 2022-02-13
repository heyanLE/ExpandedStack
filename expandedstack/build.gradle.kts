plugins {
    id ("com.android.library")
    id("kotlin-android")
    id("maven-publish")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles.add(getDefaultProguardFile("proguard-android-optimize.txt"))
            proguardFiles.add(File("proguard-rules.pro"))
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.5"
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

}
afterEvaluate{
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components["release"])
                group = "com.heyanle"
                artifactId = "ExpandedStack"
                version = "1.3"
            }
        }
    }
}


tasks.register("generateSourcesJar", Jar::class.java){
    from("android.sourceSets.main.java.srcDirs")
    classifier = "sources"
}



dependencies {

    val composeVersion = "1.0.5"

    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")
    implementation("androidx.compose.foundation:foundation-layout:$composeVersion")
    implementation("androidx.activity:activity-compose:1.4.0")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0")
    implementation ("androidx.appcompat:appcompat:1.4.1")
    implementation ("com.google.android.material:material:1.5.0")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
}