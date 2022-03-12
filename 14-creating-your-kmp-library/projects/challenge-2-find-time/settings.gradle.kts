pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "Find_Time"
include(":androidApp")
include(":shared")
include(":desktop")
include(":shared-ui")

include(":shared-logger")
includeBuild("plugins/multiplatform-swiftpackage-m1_support")
