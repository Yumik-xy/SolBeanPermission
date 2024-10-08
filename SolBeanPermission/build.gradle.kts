import java.util.Properties

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kspPlugin)
    `maven-publish`
}

android {
    namespace = "top.yumik.libs.solbeanpermission"
    compileSdk = 34

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("Publish") {
                group = readLocalProperties("lib.group")
                version = readLocalProperties("lib.version")
                description = readLocalProperties("lib.description")
                artifactId = readLocalProperties("lib.artifactId")

                from(components["release"])

                pom {
                    name = readLocalProperties("lib.artifactId")
                    description = readLocalProperties("lib.description")
                    licenses {
                        license {
                            name = "GPL-2.0"
                            url = "https://opensource.org/license/gpl-2-0"
                        }
                    }
                    developers {
                        developer {
                            id = readLocalProperties("developer.id")
                            name = readLocalProperties("developer.name")
                            email = readLocalProperties("developer.email")
                        }
                    }
                }
            }
        }

        repositories {
            maven {
                name = "remote"
                val releasesRepoUrl = uri(readLocalProperties("repo.remote"))
                val snapshotsRepoUrl = uri(readLocalProperties("repo.snapshots"))
                url = uri(
                    if (readLocalProperties("lib.version").endsWith("SNAPSHOT", true)) snapshotsRepoUrl
                    else releasesRepoUrl
                )

                credentials {
                    username = readLocalProperties("maven.username")
                    password = readLocalProperties("maven.password")
                }
            }
            maven {
                name = "local"
                val releasesRepoUrl = layout.buildDirectory.dir("repos/releases")
                val snapshotsRepoUrl = layout.buildDirectory.dir("repos/snapshots")
                url = uri(
                    if (readLocalProperties("lib.version").endsWith("SNAPSHOT", true)) snapshotsRepoUrl
                    else releasesRepoUrl
                )
            }
        }
    }
}

fun readLocalProperties(key: String): String {
    val properties = Properties()
    val inputStream = project.rootProject.file("local.properties").inputStream()
    properties.load(inputStream)
    return properties.getProperty(key)
}