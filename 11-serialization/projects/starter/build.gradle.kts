import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

buildscript {
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
  }
  dependencies {
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    classpath("com.android.tools.build:gradle:7.1.2")
    classpath("com.squareup.sqldelight:gradle-plugin:1.5.3")
  }
}

allprojects {
  repositories {
    google()
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
  }
  afterEvaluate {
    project.extensions.findByType<KotlinMultiplatformExtension>()?.let { ext ->
      ext.sourceSets.removeAll { sourceSet ->
        setOf(
          "androidAndroidTestRelease",
          "androidTestFixtures",
          "androidTestFixturesDebug",
          "androidTestFixturesRelease",
        ).contains(sourceSet.name)
      }
    }
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}