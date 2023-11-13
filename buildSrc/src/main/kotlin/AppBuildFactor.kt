import HandlerConfExt.androidTestImp
import HandlerConfExt.imp
import HandlerConfExt.testImp
import com.android.build.api.dsl.ApplicationDefaultConfig
import com.android.build.api.dsl.LibraryDefaultConfig
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler


class AndroidFactor(nameSpace : String,
                    appConfig : BaseAppModuleExtension? = null ,
                    libConfig: LibraryExtension? = null) {

    init {
        val config = appConfig ?: libConfig
        config?.let { configure ->
            configure.namespace = nameSpace
            configure.compileSdk = AppBuildConstants.compileSdk
            configure.defaultConfig {
                minSdk = AppBuildConstants.minSdk

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                if(this is LibraryDefaultConfig) {
                    consumerProguardFiles("consumer-rules.pro")
                } else if (this is ApplicationDefaultConfig) {
                    applicationId = AppBuildConstants.applicationId
                    targetSdk = AppBuildConstants.targetSdk
                    versionCode = AppBuildConstants.versionCode
                    versionName = AppBuildConstants.versionName
                }
            }

            appConfig?.buildFeatures {
                dataBinding = true
            }

            libConfig?.buildFeatures {
                dataBinding = true
            }

            configure.buildTypes {

                getByName("release") {
                    isMinifyEnabled = false
                    proguardFiles(
                        configure.getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }

            }


            configure.compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }
    }
}

object DependencyFactor {

    //dependencies
    fun DependencyHandler.retrofit() {
        imp(Dependencies.retrofit)
    }
    fun DependencyHandler.glide() {
        imp(Dependencies.glide)
    }
    fun DependencyHandler.material() {
        imp(Dependencies.dMaterial)
    }

    fun DependencyHandler.appCompat() {
        imp(Dependencies.dAppCompat)
    }

    fun DependencyHandler.core() {
        imp(Dependencies.dCoreAX)
    }

    fun DependencyHandler.splash() {
        imp(Dependencies.dSplash)
    }

    fun DependencyHandler.constraintLayout() {
        imp(Dependencies.dConstraintLayout)
    }

    fun DependencyHandler.navigation() {
        imp(Dependencies.dNavigationKFragment)
        imp(Dependencies.dNavigationKUI)
        imp(Dependencies.dNavigationKmodule)
        imp(Dependencies.dNavigationTesting)
    }

    fun DependencyHandler.unitEspresso() {
        testImp(Dependencies.tIJUnit)
        androidTestImp(Dependencies.aTIJUnit)
        androidTestImp(Dependencies.aTIEspresso)
    }

}

object HandlerConfExt {

    fun DependencyHandler.imp(name : String): Dependency? =
        add("implementation" , name)

    fun DependencyHandler.testImp(name : String) : Dependency? =
        add("testImplementation" , name)

    fun DependencyHandler.androidTestImp(name : String) : Dependency? =
        add("androidTestImplementation" , name)

}

