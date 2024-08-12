import java.util.Properties

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kspPlugin)
    `maven-publish`
}

val libGroup = "com.iflytek.ebg.xkt.lib.solbeanpermission"
val libDescription = "标准课堂学生学习项目，权限授予SDK"
val libArtifactId = "solbeanpermission"
val libVersion = "1.0.2-SNAPSHOT"

val developerId = "xyhuang23"
val developerName = "黄星宇"
val developerEmail = "xyhuang23@iflytek.com"

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
                group = libGroup
                version = libVersion
                description = libDescription
                artifactId = libArtifactId

                from(components["release"])

                pom {
                    name = libArtifactId
                    description = libDescription
                    licenses {
                        license {
                            name = "GPL-2.0"
                            url = "https://opensource.org/license/gpl-2-0"
                        }
                    }
                    developers {
                        developer {
                            id = developerId
                            name = developerName
                            email = developerEmail
                        }
                    }
                }
            }
        }

        repositories {
            maven {
                name = "remote"
                val releasesRepoUrl = uri("https://depend.iflytek.com/artifactory/ebg-mvn-release-private/")
                val snapshotsRepoUrl = uri("https://depend.iflytek.com/artifactory/ebg-mvn-snapshot-private/")
                url = uri(if (libVersion.endsWith("SNAPSHOT", true)) snapshotsRepoUrl else releasesRepoUrl)

                credentials {
                    username = readLocalProperties("maven.username")
                    password = readLocalProperties("maven.password")
                }
            }
            maven {
                name = "local"
                val releasesRepoUrl = layout.buildDirectory.dir("repos/releases")
                val snapshotsRepoUrl = layout.buildDirectory.dir("repos/snapshots")
                url = uri(if (libVersion.endsWith("SNAPSHOT", true)) snapshotsRepoUrl else releasesRepoUrl)
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