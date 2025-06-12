pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        //maven { setUrl("https://www.jitpack.io") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Korea200"
include(":app")
 