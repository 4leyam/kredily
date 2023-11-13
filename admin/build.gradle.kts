import DependencyFactor.core
import DependencyFactor.appCompat
import DependencyFactor.material
import DependencyFactor.navigation
import DependencyFactor.unitEspresso

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    AndroidFactor("com.example.admin" , libConfig = this)
}

dependencies {
    core()
    appCompat()
    material()

    navigation()
    implementation(project(":common"))

    unitEspresso()
}