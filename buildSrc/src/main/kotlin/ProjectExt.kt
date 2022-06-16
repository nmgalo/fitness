import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.applyCoreLibs() {
    dependencies {
        add("implementation", Libs.CORE_KTX)
        add("implementation", Libs.CORE_FRAGMENT_KTX)
        add("implementation", Libs.APPCOMPAT)
        add("implementation", Libs.MATERIAL)
        add("implementation", Libs.CONSTRAINT_LAYOUT)
    }
}

fun Project.applyNavigation() {
    dependencies {
        add("implementation", Libs.NAVIGATION_UI)
        add("implementation", Libs.NAVIGATION_FRAGMENT)
    }
}

fun Project.applyHilt() {
    dependencies {
        add("implementation", Libs.HILT)
        add("kapt", Libs.HILT_COMPILER)
    }
}

fun Project.applyNetwork() {
    dependencies {
        add("implementation", Libs.RETROFIT)
        add("implementation", Libs.HTTP_LOGGER)
        add("implementation", Libs.SERIALIZATION)
        add("implementation", Libs.SERIALIZATION_CONVERTER)
    }
}
