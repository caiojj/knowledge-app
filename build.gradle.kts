// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    rootProject.extra.set("kotlin_version", Versions.KOTLIN)
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Deps.buildGradle)
        classpath(Deps.gradlePlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}