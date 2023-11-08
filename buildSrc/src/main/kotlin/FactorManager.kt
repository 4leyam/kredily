import HandlerConfExt.androidTestImp
import HandlerConfExt.imp
import HandlerConfExt.testImp
import org.gradle.api.artifacts.dsl.DependencyHandler

class FactorManager {



    //dependencies
    fun DependencyHandler.retrofit() {
        imp(Dependencies.retrofit)
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

    fun DependencyHandler.unitEspresso() {
        testImp(Dependencies.tIJUnit)
        androidTestImp(Dependencies.aTIJUnit)
        androidTestImp(Dependencies.aTIEspresso)
    }

}




object HandlerConfExt {

    fun DependencyHandler.imp(name : String) {
        add("implementation" , name)
    }
    fun DependencyHandler.testImp(name : String) {
        add("testImplementation" , name)
    }
    fun DependencyHandler.androidTestImp(name : String) {
        add("androidTestImplementation" , name)
    }

}

