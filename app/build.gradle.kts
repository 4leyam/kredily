import DependencyFactor.core
import DependencyFactor.appCompat
import DependencyFactor.material
import DependencyFactor.splash
import DependencyFactor.unitEspresso
import DependencyFactor.constraintLayout
import DependencyFactor.navigation


plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    AndroidFactor("com.example.kredily" , this)
}

dependencies {
    core()
    appCompat()
    constraintLayout()
    material()

    splash()
    navigation()

    implementation(project(":common"))
    implementation(project(":admin"))
    implementation(project(":frshome"))
    implementation(project(":onBoarding"))


    unitEspresso()
}