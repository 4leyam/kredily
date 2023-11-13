import DependencyFactor.core
import DependencyFactor.appCompat
import DependencyFactor.glide
import DependencyFactor.material
import DependencyFactor.navigation
import DependencyFactor.unitEspresso

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    AndroidFactor("com.example.frshome" , libConfig = this)
}

dependencies {
    core()
    appCompat()
    material()

    navigation()
    glide()

    implementation(project(":common"))

    unitEspresso()
}