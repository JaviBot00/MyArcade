plugins {
    id("com.android.application")
    kotlin("android")
}

val appName = "MyArcade"
val gdxVersion = "1.12.0"
val roboVMVersion = "2.3.16"
val box2DLightsVersion = "1.5"
val ashleyVersion = "1.7.4"
val aiVersion = "1.8.2"
val gdxControllersVersion = "2.2.1"

android {
    namespace = "com.hotguy.myarcade"
    compileSdk = 34
//    buildToolsVersion = "33.0.2"

    defaultConfig {
        applicationId = "com.hotguy.myarcade"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    sourceSets {
        named("main") {
            jniLibs.srcDirs("libs")
        }
    }

    buildFeatures {
        renderScript = true
        aidl = true
    }
}

configurations {
    create("natives")
}

dependencies {

//    LibGDX dependencies
    implementation(fileTree("libs") { include("*.jar") })

//    implementation "com.badlogicgames.gdx:gdx-backend-android:$gdxVersion"
    api("com.badlogicgames.gdx:gdx-backend-android:$gdxVersion")
    api("com.badlogicgames.gdx:gdx:$gdxVersion")

    configurations.named("natives") {
        dependencies {
//    natives("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-armeabi)"
            implementation("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-armeabi-v7a")
            implementation("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-arm64-v8a")
            implementation("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-x86")
            implementation("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-x86_64")
        }
//    implementation("com.badlogicgames.gdx:gdx-box2d:$gdxVersion")
////    natives("com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-armeabi")
//    natives("com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-armeabi-v7a")
//    natives("com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-arm64-v8a")
//    natives("com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-x86")
//    natives("com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-x86_64")
//
//    implementation ("com.badlogicgames.gdx:gdx:$gdxVersion")
//    implementation ("com.badlogicgames.gdx:gdx-box2d:$gdxVersion")
//
//    implementation("com.badlogicgames.gdx:gdx-backend-lwjgl:$gdxVersion")
//    implementation("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop")
//    implementation("com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-desktop")

//    Android dependencies
        implementation("androidx.core:core-ktx:1.12.0")
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.9.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    }

    tasks.register("copyAndroidNatives") {
        doLast {
//    file("libs/armeabi/").mkdirs()
            file("libs/armeabi-v7a/").mkdirs()
            file("libs/arm64-v8a/").mkdirs()
            file("libs/x86/").mkdirs()
            file("libs/x86_64/").mkdirs()

            configurations.getByName("natives").copy().forEach { jar ->
                val outputDir = when {
                    jar.name.endsWith("natives-armeabi-v7a.jar") -> file("libs/armeabi-v7a")
//            jar.name.endsWith("natives-armeabi.jar") -> file("libs/armeabi")
                    jar.name.endsWith("natives-arm64-v8a.jar") -> file("libs/arm64-v8a")
                    jar.name.endsWith("natives-x86.jar") -> file("libs/x86")
                    jar.name.endsWith("natives-x86_64.jar") -> file("libs/x86_64")
                    else -> null
                }

                outputDir?.let { dir ->
                    copy {
                        from(zipTree(jar))
                        into(dir)
                        include("*.so")
                    }
                }
            }
        }
    }
}
//tasks.whenTaskAdded { packageTask ->
//    if (packageTask.name.contains("merge") && packageTask.name.contains("JniLibFolders")) {
//        packageTask.dependsOn 'copyAndroidNatives'
//    }
//}
//
//task run(type: Exec) {
//    def path
//    def localProperties = project.file("../local.properties")
//    if (localProperties.exists()) {
//        Properties properties = new Properties()
//        localProperties.withInputStream { instr ->
//            properties.load(instr)
//        }
//        def sdkDir = properties.getProperty('sdk.dir')
//        if (sdkDir) {
//            path = sdkDir
//        } else {
//            path = "$System.env.ANDROID_HOME"
//        }
//    } else {
//        path = "$System.env.ANDROID_HOME"
//    }
//
//    def adb = path + "/platform-tools/adb"
//    commandLine "$adb", 'shell', 'am', 'start', '-n', 'com.hotguy.myarcade/com.hotguy.myarcade.MainActivity'
//}